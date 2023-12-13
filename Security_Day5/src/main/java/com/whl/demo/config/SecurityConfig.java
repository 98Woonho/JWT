package com.whl.demo.config;

import com.whl.demo.config.auth.ExceptionHandler.CustomAccessDeniedHandler;
import com.whl.demo.config.auth.ExceptionHandler.CustomAuthenticationEntryPoint;
import com.whl.demo.config.auth.loginHandler.CustomAuthenticationFailureHandler;
import com.whl.demo.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.whl.demo.config.auth.logoutHandler.CustomLogoutHandler;
import com.whl.demo.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity // Security용 annotation
public class SecurityConfig {

    @Autowired
    private HikariDataSource dataSource;

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
                    authorize.requestMatchers("/join").hasRole("ANONYMOUS"); // 비로그인 계정이라면 /join 접근 허용
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
                    login.successHandler(new CustomLoginSuccessHandler()); // 로그인 성공 시 Handler
                    login.failureHandler(new CustomAuthenticationFailureHandler()); // 로그인 인증 실패 Handler
                }
        );

        // 로그아웃
        http.logout(
                logout -> {
                    logout.permitAll();
                    logout.logoutUrl("/logout");
                    logout.addLogoutHandler(new CustomLogoutHandler()); // 로그아웃 처리 Handler
                    logout.logoutSuccessHandler(customLogoutSuccessHandler()); // 로그아웃 성공 시 Handler
                }
        );

        // 예외처리
        http.exceptionHandling(
                ex -> {
                    ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint()); // 인증되지 않은 회원이 페이지로 바로 접근할려고 할 때 예외 발생
                    ex.accessDeniedHandler(new CustomAccessDeniedHandler()); // 권한(ROLE)이 없는 계정으로 페이지 접속 했을 때 예외 발생 (ex. user 계정으로 member 페이지 접속)
                }
        );

        // rememberMe
        http.rememberMe(
                rm -> {
                    rm.key("rememberMeKey");
                    rm.rememberMeParameter("remember-me");
                    rm.alwaysRemember(false);
                    rm.tokenValiditySeconds(3600);
                    rm.tokenRepository(tokenRepository());
                }
        );

        // Oauth2
        http.oauth2Login(
                oauth2 -> {
                    oauth2.loginPage("/login");
                }
        );

        return http.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        // repo.setCreateTableOnStartup(true); // 테이블을 만들어주는 set. 이 코드 없이 resource/data.sql에 SQL문을 만들어놓으면 JPA가 알아서 sql 파일을 읽어서 SQL문을 실행해 줌.
        repo.setDataSource(dataSource);
        return repo;
    }

    // [공부] CustomLogoutSuccessHandler에서 @Value를 사용하고 싶은데 CustomLogoutSuccessHandler가 Bean으로 등록이 안 되어 있어서 사용을 못 함. 그래서 빈으로 등록.
    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    // BCryptPasswordEncoder Bean 등록 - 패스워드 검증에 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
