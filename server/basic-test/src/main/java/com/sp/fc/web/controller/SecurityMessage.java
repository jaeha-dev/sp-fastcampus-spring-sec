package com.sp.fc.web.controller;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@Data
@NoArgsConstructor
public class SecurityMessage {

    private Authentication authentication;
    private String message;

    @Builder
    public SecurityMessage(Authentication authentication, String message) {
        this.authentication = authentication;
        this.message = message;
    }
}