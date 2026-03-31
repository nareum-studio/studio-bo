package com.nareumadmin.error.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ServiceError {

    NOT_EXIST_TEAM(HttpStatus.BAD_REQUEST, "존재하지 않는 팀입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
