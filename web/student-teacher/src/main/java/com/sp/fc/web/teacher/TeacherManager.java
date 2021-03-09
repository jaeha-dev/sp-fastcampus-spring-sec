package com.sp.fc.web.teacher;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TeacherManager implements AuthenticationProvider, InitializingBean {

    // 현재 DB 연동을 하지 않으므로 가상의 데이터를 사용한다고 가정한다.
    private final Map<String, Teacher> teachers = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TeacherAuthenticationToken token = (TeacherAuthenticationToken) authentication;

        if (teachers.containsKey(token.getCredentials())) {
            Teacher teacher = teachers.get(token.getCredentials());

            return TeacherAuthenticationToken.builder()
                    .principal(teacher)
                    .details(teacher.getName())
                    .authorities(teacher.getRoles())
                    .authenticated(true)
                    .build();
        }

        return null;
    }

    @Override // 인증 토큰을 받을 때 인증을 수행하도록 커스텀 프로바이더를 지정한다.
    public boolean supports(Class<?> authentication) {
        return authentication == TeacherAuthenticationToken.class;
    }

    @Override // 맵을 초기화한다.
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Teacher("T1", "선생님1", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null)
        ).forEach(set -> teachers.put(set.getId(), set));
    }
}