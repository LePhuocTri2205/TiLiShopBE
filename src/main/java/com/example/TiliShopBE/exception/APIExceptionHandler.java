package com.example.TiliShopBE.exception;

import com.example.TiliShopBE.exception.exceptions.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

    // Validation lỗi (field không hợp lệ)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleBadRequest(MethodArgumentNotValidException exception) {
        StringBuilder message = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            message.append(fieldError.getDefaultMessage()).append(". ");
        }
        return ResponseEntity.badRequest().body(message.toString().trim());
    }

    // Sai mật khẩu hoặc không tìm thấy số điện thoại
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<String> handleBadCredentials(Exception exception) {
        return ResponseEntity.status(401)
                .body("Số điện thoại hoặc mật khẩu không chính xác. Vui lòng thử lại.");
    }

    // Số điện thoại đã tồn tại khi đăng ký
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    // Chưa đăng nhập / token hết hạn / token không hợp lệ
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException exception) {
        return ResponseEntity.status(401)
                .body("Bạn chưa đăng nhập hoặc phiên làm việc đã hết hạn. Vui lòng đăng nhập lại.");
    }
}
