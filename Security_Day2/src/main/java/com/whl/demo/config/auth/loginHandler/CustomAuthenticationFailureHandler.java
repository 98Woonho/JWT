package com.whl.demo.config.auth.loginHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendRedirect("/login?error=" + URLEncoder.encode(exception.getMessage(), "UTF-8")); // exception.getMessage()가 한글이므로 UTF-8로 encoding
    }
}
