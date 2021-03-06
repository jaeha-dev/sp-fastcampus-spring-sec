package com.sp.fc.web.teacher;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// 인증 제공자
@Component
public class TeacherManager implements AuthenticationProvider, InitializingBean {

    private final Map<String, Teacher> teachers = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // if (teachers.containsKey(token.getName())) {
        //     Teacher teacher = teachers.get(token.getName());
        //
        //     return TeacherAuthenticationToken.builder()
        //             .credentials("empty")
        //             .principal(teacher)
        //             .details("empty")
        //             .authenticated(true)
        //             .build();
        // }
        // return null;

        // (라디오 박스 값 추가 이후)
        TeacherAuthenticationToken token = (TeacherAuthenticationToken) authentication;

        // 실제 Credentials은 객체(인증 수행에 필요한 ID, Password 등)임을 유의한다.
        // (Suspicious call to 'Map.containsKey' 경고는 리터럴 값을 매개변수로 전달해서 발생하는 것)
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

    @Override
    public boolean supports(Class<?> authentication) {
        // return authentication == UsernamePasswordAuthenticationToken.class;

        // (라디오 박스 값 추가 이후)
        return authentication == TeacherAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Teacher("T1", "선생님1", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))),
                new Teacher("T2", "선생님2", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))),
                new Teacher("T3", "선생님3", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")))
        ).forEach(set -> teachers.put(set.getId(), set));
    }
}