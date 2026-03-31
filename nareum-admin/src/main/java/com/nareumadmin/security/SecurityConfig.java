package com.nareumadmin.security;

import com.nareumadmin.service.CustomUserDetailsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF - 쿠키 방식 사용 시 설정 필요
            .csrf(csrf -> csrf.disable()
                // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // .ignoringRequestMatchers("/auth/**")
            )
            // 세션 사용 (쿠키로 세션 ID 전달)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            // 폼 로그인 비활성화 (직접 API로 처리)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .userDetailsService(customUserDetailsService)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .logout(logout -> logout.logoutUrl("/auth/logout").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").logoutSuccessHandler((req, res, auth) -> {
                    res.setStatus(200);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"message\":\"로그아웃 성공\"}");
                }));
            // .exceptionHandling(
            //     ex -> ex.authenticationEntryPoint((request, response, authException) -> {
            //         response.setStatus(401);
            //         response.setContentType("application/json;charset=UTF-8");
            //         response.getWriter().write("{\"message\": \"로그인이 필요합니다.\"}");
            //     }).accessDeniedHandler((request, response, accessDeniedException) -> {
            //         response.setStatus(403);
            //         response.setContentType("application/json;charset=UTF-8");
            //         response.getWriter().write("{\"message\": \"권한이 없습니다.\"}");
            //     }));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
        throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowedOrigins(List.of("*")); // 프론트 주소
        config.setAllowedOriginPatterns(List.of("*")); // 테스트를 위한 설정
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}