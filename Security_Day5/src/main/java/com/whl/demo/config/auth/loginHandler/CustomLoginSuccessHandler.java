package com.whl.demo.config.auth.loginHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("CustomLoginSuccessHandler's onAuthenticationSuccess! ");

        // Collection<? extends GrantedAuthority> : 제네릭으로 `GrantedAuthority`를 확장하는 어떤 객체든 담을 수 있음. `GrantedAuthority`는 Spring Security에서 권한을 나타내는 인터페이스
        Collection<? extends GrantedAuthority> collection = authentication.getAuthorities();
        collection.forEach(
                (role) -> {
                    System.out.println("[CustomLoginSuccessHandler] onAuthenticationSuccess role : " + role.getAuthority());
                    String role_str = role.getAuthority();
                    try {
                        if(role_str.equals("ROLE_USER")) {
                            response.sendRedirect("/user"); // role = ROLE_USER이면 user 페이지로 Redirect
                        } else if(role_str.equals("ROLE_MEMBER")) {
                            response.sendRedirect("/member"); // role = ROLE_MEMBER이면 user 페이지로 Redirect
                        } else if(role_str.equals("ROLE_ADMIN")) {
                            response.sendRedirect("/admin"); // role = ROLE_ADMIN이면 user 페이지로 Redirect
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

}
