package com.sp.fc.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    // login, logout 경로는 기본적으로 제공된다.
    // (DefaultLoginPageGeneratingFilter/DefaultLogoutPageGeneratingFilter 클래스에서,
    // /login, /logout 경로와 HTML 웹 페이지(로그인 폼, 로그아웃)를 제공한다.

    @RequestMapping("/auth")
    public Authentication authentication() {
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @RequestMapping("/user")
    public SecurityMessage userRole() {
        return SecurityMessage.builder()
                .authentication(SecurityContextHolder.getContext().getAuthentication())
                .message("User 정보")
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @RequestMapping("/admin")
    public SecurityMessage adminRole() {
        return SecurityMessage.builder()
                .authentication(SecurityContextHolder.getContext().getAuthentication())
                .message("Admin 정보")
                .build();
    }
}