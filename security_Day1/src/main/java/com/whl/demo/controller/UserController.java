package com.whl.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UserController {

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

}
