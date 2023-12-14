package com.whl.demo.config.auth.ExceptionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("CustomAuthenticationEntryPoint] authException : " + authException); // 인증되지 않은 회원이 페이지로 바로 접근할려고 할 때 예외 발생
        response.sendRedirect("/login?error=" + authException.getMessage());
    }
}
