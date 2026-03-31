package com.nareumadmin.error.exception;


import com.nareumadmin.error.type.ServiceError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    HttpStatus httpStatus;
    String message;

    public ServiceException(ServiceError serviceError) {
        this.httpStatus = serviceError.getHttpStatus();
        this.message = serviceError.getMessage();
    }


}
