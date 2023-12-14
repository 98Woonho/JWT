package com.whl.demo.controller;

import com.whl.demo.domain.dto.UserDto;
import com.whl.demo.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public void login() {
        log.info("GET /login...");
    }

    @GetMapping("/user")
    public void user(Authentication authentication, Model model) {
        log.info("GET /user...Authentication : " + authentication); // 로그인 한 사용자의 정보들이 담겨있음.
        log.info("username : " + authentication.getName());
        log.info("principal : " + authentication.getPrincipal());
        log.info("authorities : " + authentication.getAuthorities());
        log.info("datails : " + authentication.getDetails());
        log.info("credentials : " + authentication.getCredentials());

        model.addAttribute("authentication", authentication);
    }
    @GetMapping("/member")
    public void member() {
        log.info("GET /member");

        // 위와 다른 방법으로 authentication 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("GET /member...Authentication : " + authentication); // 로그인 한 사용자의 정보들이 담겨있음.
        log.info("username : " + authentication.getName());
        log.info("principal : " + authentication.getPrincipal());
        log.info("authorities : " + authentication.getAuthorities());
        log.info("datails : " + authentication.getDetails());
        log.info("credentials : " + authentication.getCredentials());
    }

    @GetMapping("/admin")
    public void admin() {
        log.info("GET /admin");
    }

    @GetMapping("/join")
    public void join() {
        log.info("GET /join");
    }

    @PostMapping("/join")
    public String join_post(UserDto dto) {
        log.info("POST /join...dto " + dto);
        // 파라미터 받기
        // 입력값 검증 (유효성 체크)

        // 서비스 실행
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        boolean isJoin = userService.memberJoin(dto);
        // View로 속성 등등 전달
        if (isJoin)
            return "redirect:login?msg=MemberJoin Success!";
        else
            return "forward:join?msg=Join Failed....";

        // +a 예외처리
    }
}
