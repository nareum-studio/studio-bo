package com.nareumadmin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, String> map;

        if (request.getRequestedSessionId() != null
            && !request.isRequestedSessionIdValid()) {
            map = Map.of("code", "SESSION_EXPIRED", "message", "세션이 만료되었습니다.");
        } else {
            map = Map.of("code", "UNAUTHORIZED", "message", "로그인이 필요합니다.");
        }

        objectMapper.writeValue(response.getOutputStream(), map);
    }
}
