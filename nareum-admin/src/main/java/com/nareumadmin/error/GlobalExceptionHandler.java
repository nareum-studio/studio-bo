package com.nareumadmin.error;

import com.nareumadmin.common.dto.ResponseDTO;
import com.nareumadmin.error.exception.AuthException;
import com.nareumadmin.error.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authException(AuthException e) {
        ResponseDTO<?> response = ResponseDTO.builder()
            .message(e.getMessage())
            .data(null)
            .build();

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceException(ServiceException e) {
        ResponseDTO<?> response = ResponseDTO.builder()
            .message(e.getMessage())
            .data(null)
            .build();

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

}
