package com.nareumadmin.error.exception;

import com.nareumadmin.error.type.AuthError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

@Getter
public class AuthException extends InternalAuthenticationServiceException {

    HttpStatus httpStatus;
    String message;


    public AuthException(AuthError authError) {
        super(authError.getMessage());
        this.httpStatus = authError.getHttpStatus();
        this.message = authError.getMessage();
    }
}
