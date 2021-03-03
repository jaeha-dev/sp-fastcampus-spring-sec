package com.sp.fc.config;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestInfo {

    private String remoteIp;
    private String sessionId;
    private LocalDateTime loginTime;

    @Builder
    public RequestInfo(String remoteIp, String sessionId, LocalDateTime loginTime) {
        this.remoteIp = remoteIp;
        this.sessionId = sessionId;
        this.loginTime = loginTime;
    }
}