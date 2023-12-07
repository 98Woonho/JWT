package com.whl.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Security용 annotation
public class SecurityConfig {
    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        // CSRF 비활성화
        http.csrf(
                config -> {
                    config.disable();
                }
        );

        // 요청 URL별 접근 제한
        http.authorizeHttpRequests(
                authorize -> {
                    // 사용자의 요청을 선별 및 접근 허용
                    authorize.requestMatchers("/", "/login").permitAll(); // permitAll() : ROLE(역할) 체크 없이 접근 허용
                    authorize.requestMatchers("/user").hasRole("USER"); // ROLE_USER (이)라는 역할을 가진 계정만 /user 접근 허용
                    authorize.requestMatchers("/member").hasRole("MEMBER"); // ROLE_MEMBER (이)라는 역할을 가진 계정만 /member 접근 허용
                    authorize.requestMatchers("/admin").hasRole("ADMIN"); // ROLE_ADMIN (이)라는 역할을 가진 계정만 /admin 접근 허용
                    authorize.anyRequest().authenticated(); // 그 외 나머지 요청은 인증을 받아야 접근 가능
                }
        );

        /** 위 코드 이거랑 같음.
         http.authorizeRequests()
         .requestMatchers("/", "/login").permitAll()
         .requestMatchers("/user").hasRole("USER")
         .requestMatchers("/member").hasRole("MEMBER")
         .requestMatchers("/admin").hasRole("ADMIN")
         .anyRequest().authenticated();
         */

        // 로그인
        http.formLogin(
                login -> {
                    login.permitAll();
                    login.loginPage("/login");
                }
        );


        // 로그아웃
        http.logout(
                logout -> {
                    logout.permitAll();
                    logout.logoutUrl("/logout");
                }
        );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build());

        userDetailsManager.createUser(User.withUsername("member")
                .password(passwordEncoder.encode("1234"))
                .roles("MEMBER")
                .build());

        userDetailsManager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("1234"))
                .roles("ADMIN")
                .build());

        return userDetailsManager;
    }

    // BCryptPasswordEncoder Bean 등록 - 패스워드 검증에 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
