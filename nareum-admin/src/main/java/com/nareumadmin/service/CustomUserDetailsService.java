package com.nareumadmin.service;

import com.nareumadmin.error.exception.AuthException;
import com.nareumadmin.error.type.AuthError;
import com.nareumadmin.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberMapper.findById(id)
            .orElseThrow(() -> new AuthException(AuthError.USER_NOT_FOUND));
    }
}
