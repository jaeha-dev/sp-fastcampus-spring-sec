package com.sp.fc.web.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAuthenticationToken implements Authentication {

    private Student principal; // 인증 수행 후, 결과 (Output)
    private String credentials; // 인증 수행에 필요한 정보 (ID, Password 등)
    private String details; // 인증과 관련한 기타 정보
    private Set<GrantedAuthority> authorities; // 권한 정보

    private boolean authenticated; // 인증 수행 여부

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보
        return principal == null ? new HashSet<>() : principal.getRoles();
    }

    @Override
    public String getName() {
        return principal == null ? "" : principal.getName();
    }
}