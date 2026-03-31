package com.nareumadmin.service;

import com.nareumadmin.domain.Member;
import com.nareumadmin.dto.AuthDTO;
import com.nareumadmin.dto.AuthDTO.SignupResponse;
import com.nareumadmin.error.exception.AuthException;
import com.nareumadmin.error.type.AuthError;
import com.nareumadmin.mapper.MemberMapper;
import com.nareumadmin.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthDTO.SignupResponse signup(AuthDTO.SignupRequest request) {
        if (memberMapper.findById(request.getId()).isPresent()) {
            throw new AuthException(AuthError.ALREADY_EXIST_ID);
        }

        String encodedPw = passwordEncoder.encode(request.getPassword());

        Member member = Member.builder()
            .id(request.getId())
            .password(encodedPw)
            .name(request.getName())
            .phone(request.getPhone())
            .role(request.getRole())
            .status(Status.ACTIVE)
            .build();

        memberMapper.signup(member);

        return SignupResponse.builder()
            .id(member.getId())
            .name(member.getName())
            .phone(member.getPhone())
            .role(member.getRole())
            .status(member.getStatus())
            .build();
    }


}
