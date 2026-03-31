package com.nareumadmin.dto;

import com.nareumadmin.type.Role;
import com.nareumadmin.type.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupRequest {
        private String id;
        private String password;
        private String name;
        private String phone;
        private Role role;
        private Status status;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupResponse {
        private String id;
        private String name;
        private String phone;
        private Role role;
        private Status status;
    }

    @Getter
    public static class LoginRequest {
        private String id;
        private String password;
    }

    @Getter
    @Setter
    public static class LoginResponse {
        private String name;
    }

}
