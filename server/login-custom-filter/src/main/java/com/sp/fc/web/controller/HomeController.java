package com.sp.fc.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/login")
    public String login() {
        return "LoginForm";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);

        return "LoginForm";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "AccessDenied";
    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication auth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}