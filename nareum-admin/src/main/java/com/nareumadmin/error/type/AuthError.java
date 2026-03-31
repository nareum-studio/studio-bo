package com.nareumadmin.error.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthError {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NEED_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),
    DIFFERENT_PASSWORD_CHECK(HttpStatus.BAD_REQUEST, "새 비밀번호가 일치하지 않습니다."),
    ALREADY_EXIST_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
