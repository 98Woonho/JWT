package com.whl.demo.config.auth.logoutHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // System.out.println("[CustomLogoutHandler] logout()");
        HttpSession session = request.getSession(false); // session을 가져오는데, session이 없으면 새로 생성하지 말고 null을 반환
        if(session != null) {
            session.invalidate(); // 로그아웃 시, session 데이터 제거
        }
    }
}
