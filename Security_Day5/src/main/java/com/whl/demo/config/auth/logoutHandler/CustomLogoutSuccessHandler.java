package com.whl.demo.config.auth.logoutHandler;

import com.whl.demo.config.auth.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;


public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    // 카카오 계정과 함께 로그아웃

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}") // [공부] @Value : property 값을 필드에 주입해주는 Annotation. @Value를 사용할려면 @Value를 사용하고 있는 메서드의 클래스가 Bean으로 등록이 되어 있어야 함.
    private String kakaoClientId; // 로그아웃에 필요한 client_id(앱 REST KEY)

    private final String Redirect_Uri = "http://localhost:8080/login"; // 로그아웃에 필요한 logout_redirect_uri([내 애플리케이션] > [카카오 로그인] > [Logout Redirect URI]에 등록된 값 중 하나)

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("[CustomLogoutSuccessHandler] onLogoutSuccess()");

        // provider 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        String provider = principalDetails.getUserDto().getProvider();
        System.out.println("[CustomLogoutSuccess] logout() provider : " + provider);

        // provider에 따른 로그아웃 설정
        if (provider != null && provider.equals("kakao")) {
            // https://kauth.kakao.com/oauth/logout 링크에 client_id와 logout_redirect_uri를 파라미터로 보내주면 GET으로 처리해서 카카오 계정과 함께 로그아웃을 진행해줌.
            String url = "https://kauth.kakao.com/oauth/logout?client_id=" + kakaoClientId + "&logout_redirect_uri=" + Redirect_Uri;
            response.sendRedirect(url);

            return ;
        } else if (provider != null && provider.equals("naver")) {
            return ;
        } else if (provider != null && provider.equals("google")) {
            return ;
        }

        // 일반 로그아웃
        HttpSession session = request.getSession(false); // session을 가져오는데, session이 없으면 새로 생성하지 말고 null을 반환
        if(session != null) {
            session.invalidate(); // 로그아웃 시, session 데이터 제거
        }

        response.sendRedirect("/"); // request.getContextPath() == "/";
    }
}
