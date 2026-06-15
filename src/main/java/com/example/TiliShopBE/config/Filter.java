package com.example.TiliShopBE.config;

import com.example.TiliShopBE.entity.Account;
import com.example.TiliShopBE.exception.exceptions.AuthenticationException;
import com.example.TiliShopBE.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Autowired
    TokenService tokenService;

    private static final List<String> SWAGGER_PATHS = List.of(
            "/swagger-ui",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars"
    );

    private final List<String> PUBLIC_API = List.of(
            "POST:/api/register",
            "POST:/api/login"
    );

    public boolean isPublicAPI(String uri, String method) {
        AntPathMatcher matcher = new AntPathMatcher();

        return PUBLIC_API.stream().anyMatch(pattern -> {
            String[] parts = pattern.split(":", 2);
            if (parts.length != 2) return false;

            String allowedMethod = parts[0];
            String allowedUri = parts[1];
            return allowedMethod.equalsIgnoreCase(method) && matcher.match(allowedUri, uri);
        });
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return SWAGGER_PATHS.stream().anyMatch(uri::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filter running...");
        String uri = request.getRequestURI();
        String method = request.getMethod();

        if (isPublicAPI(uri, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        if (token == null || token.isEmpty()) {
            resolver.resolveException(request, response, null, new AuthenticationException("Empty token"));
            return;
        }

        // => Có token rồi, kiểm tra token có hợp lệ không
        Account account = null;
        try {
            account = tokenService.extractToken(token);
        } catch (ExpiredJwtException expiredJwtException) {
            resolver.resolveException(request, response, null, new AuthenticationException("Expired token"));
            return;
        } catch (MalformedJwtException malformedJwtException) {
            resolver.resolveException(request, response, null, new AuthenticationException("Invalid token"));
            return;
        }

        // Lưu thông tin account đang request vô session
        UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(account, token, account.getAuthorities());
        authenToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenToken);

        // Token hợp lệ, cho phép truy cập vào hệ thống
        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) return null;
        if (authHeader.length() < 7) return null;
        return authHeader.substring(7);
    }
}
