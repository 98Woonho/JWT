package com.whl.demo.config.auth;


import com.whl.demo.config.auth.provider.KakaoUserInfo;
import com.whl.demo.config.auth.provider.OAuth2UserInfo;
import com.whl.demo.domain.dto.UserDto;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalDetailsOAuth2Service extends DefaultOAuth2UserService {

    // <a href="/oauth2/authorization/kakao">카카오 로그인</a><br/> 클릭 시 메서드 실행
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() userRequest : "+userRequest);
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() userRequest.getClientRegistration() : "+userRequest.getClientRegistration());
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() userRequest.getAccessToken() : "+userRequest.getAccessToken());
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() userRequest.getAdditionalParameters() : "+userRequest.getAdditionalParameters());
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() userRequest.getAccessToken().getTokenValue() : "+userRequest.getAccessToken().getTokenValue());
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() userRequest.getAccessToken().getTokenType().getValue() : "+userRequest.getAccessToken().getTokenType().getValue());
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() userRequest.getAccessToken().getAccessToken().getScopes() : "+userRequest.getAccessToken().getScopes());

        //Attribute확인
        OAuth2User oAuth2User =    super.loadUser(userRequest);
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() oAuth2User : " + oAuth2User);
        System.out.println("[PrincipalDetailsOAuth2Service] loadUser() oAuth2User.getAttributes() : " + oAuth2User.getAttributes());

        //OAuth Server Provider 구별
        String provider = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("[PrincipalDetailsOAuth2Service] provider : " + provider);

        OAuth2UserInfo oAuth2UserInfo = null;
        if(provider!=null&&provider.equals("kakao")){
            String id = oAuth2User.getAttributes().get("id").toString();
            KakaoUserInfo kakaUserInfo = new KakaoUserInfo(id, (Map<String, Object>)oAuth2User.getAttributes().get("properties") );
            System.out.println("[PrincipalDetailsOAuth2Service] kakaUserInfo : " + kakaUserInfo );
            oAuth2UserInfo = kakaUserInfo;
        }else if(provider!=null&&provider.equals("naver")){

        }else if(provider!=null&&provider.equals("google")){

        }
        System.out.println("[PrincipalDetailsOAuth2Service] oAuth2UserInfo : " + oAuth2UserInfo);



        //PrincipalDetails생성
        UserDto dto = new UserDto();
        dto.setUsername("TMP_KAKAO_"+oAuth2UserInfo.getProviderId());
        dto.setPassword("1234");
        dto.setRole("ROLE_USER");

        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setAttributes(oAuth2UserInfo.getAttributes());
        principalDetails.setAccessToken(userRequest.getAccessToken().getTokenValue());
        principalDetails.setUserDto(dto);
        return principalDetails;
    }


}