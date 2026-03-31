package com.nareumadmin.controller;

import com.nareumadmin.common.dto.ResponseDto;
import com.nareumadmin.dto.AuthDTO;
import com.nareumadmin.error.exception.AuthException;
import com.nareumadmin.error.type.AuthError;
import com.nareumadmin.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthDTO.SignupRequest request) {
        AuthDTO.SignupResponse response = authService.signup(request);

        return ResponseEntity.ok(new ResponseDto<>("회원가입 성공", response));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO.LoginRequest request,
        HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword())
            );

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
            );

            return ResponseEntity.ok(new ResponseDto<>("로그인 성공", null));
        } catch (BadCredentialsException e) {
            throw new AuthException(AuthError.UNAUTHORIZED);
        }
    }
}
