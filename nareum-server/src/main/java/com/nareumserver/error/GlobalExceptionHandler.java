package com.nareumserver.error;

import com.nareumserver.common.dto.ResponseDTO;
import com.nareumserver.error.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceException(ServiceException e) {
        ResponseDTO<?> response = ResponseDTO.builder()
            .message(e.getMessage())
            .data(null)
            .build();

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

}
