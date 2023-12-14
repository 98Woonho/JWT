package com.whl.demo.config.auth.logoutHandler;

import com.whl.demo.config.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class CustomLogoutHandler implements LogoutHandler {
    private RestTemplate restTemplate;

    public CustomLogoutHandler() {
        restTemplate = new RestTemplate();
    }

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("[CustomLogoutHandler] logout()");
        // 카카오 로그아웃

        // provider 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        String provider = principalDetails.getUserDto().getProvider();
        System.out.println("[CustomLogoutHandler] logout() provider : " + provider);

        if (provider != null && provider.equals("kakao")) {
            // AccessToken 추출
            String accessToken = principalDetails.getAccessToken();
            // Request URL
            String url = "https://kapi.kakao.com/v1/user/logout";
            // Request Header
            HttpHeaders headers = new HttpHeaders();
            headers.add("ConTent-Type", "application/x-www-form-urlencoded");
            headers.add("Authorization", "Bearer " + accessToken);

            // parameter(생략)
            // MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

            // Header + Parameter 단위 생성
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

            // RestTemplate에 HttpEntity 등록
            ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println("[CustomLogoutHandler] logout() resp : " + resp);
            System.out.println("[CustomLogoutHandler] logout() resp.getBody() : " + resp.getBody());

        } else if (provider != null && provider.equals("naver")) {
            // AccessToken 추출
            String accessToken = principalDetails.getAccessToken();

            // Request URL
            String url = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=" + naverClientId + "&client_secret=" + naverClientSecret + "&access_token=" + accessToken + "&service_provider=NAVER";

            restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        } else if (provider != null && provider.equals("google")) {

        }

        HttpSession session = request.getSession(false); // session을 가져오는데, session이 없으면 새로 생성하지 말고 null을 반환
        if(session != null) {
            session.invalidate(); // 로그아웃 시, session 데이터 제거
        }

    }
}
