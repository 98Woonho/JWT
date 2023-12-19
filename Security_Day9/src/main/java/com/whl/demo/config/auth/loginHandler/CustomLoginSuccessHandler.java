package com.whl.demo.config.auth.loginHandler;

import com.whl.demo.config.auth.PrincipalDetails;
import com.whl.demo.config.auth.jwt.JwtProperties;
import com.whl.demo.config.auth.jwt.JwtTokenProvider;
import com.whl.demo.config.auth.jwt.TokenInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // /login post로 submit 했을 때
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // ---------------------------------------------------------------------------------------
        // JWT ADD
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        // 쿠키 생성
        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, tokenInfo.getAccessToken());
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키의 만료시간 설정
        cookie.setPath("/");
        response.addCookie(cookie);
        // ---------------------------------------------------------------------------------------

        Collection<? extends GrantedAuthority> collection = authentication.getAuthorities();
        System.out.println("[CustomLoginSuccessHandler] onAuthenticationSuccess()!!!");
        collection.forEach(
                (role) -> {
                     System.out.println("[CustomLoginSuccessHandler] onAuthenticationSuccess role : " + role); // 로그인 시 넘어오는 role 테스트
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
