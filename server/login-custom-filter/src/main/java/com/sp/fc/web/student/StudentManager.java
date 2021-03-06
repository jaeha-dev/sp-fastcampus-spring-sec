package com.sp.fc.web.student;

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
// DB를 연동하는 경우, DaoAuthenticationProvider 클래스와 UserDetailsService, UserDetails 클래스를 사용하여 위임하므로
// 아래의 인증 프로바이더를 실제 구현하는 경우는 많지 없다.
@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    // 현재 DB 연동을 하지 않으므로 가상의 데이터를 사용한다고 가정한다.
    private final Map<String, Student> students = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // 토큰에서 꺼낸 이름이 맵에 포함된 경우, 인증 토큰을 발급한다.
        // if (students.containsKey(token.getName())) {
        //     Student student = students.get(token.getName());

        //     return StudentAuthenticationToken.builder()
        //             .credentials("empty")
        //             .principal(student) // 인증 수행 후, 인증된 학생 객체를 전달하는 것이 주 목적이다.
        //             .details("empty")
        //             .authenticated(true) // 인증 성공을 표시한다.
        //             .build();
        // }

        // 인증되지 않은 경우(처리할 수 없는 경우)는 null을 반환한다.
        // (authenticated(false)한 객체를 반환할 경우, 인증 과정을 핸들링 한 의미가 되므로 문제가 된다.)
        // return null;

        // (라디오 박스 값 추가 이후)
        StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;

        // 실제 Credentials은 객체(인증 수행에 필요한 ID, Password 등)임을 유의한다.
        // (Suspicious call to 'Map.containsKey' 경고는 리터럴 값을 매개변수로 전달해서 발생하는 것)
        if (students.containsKey(token.getCredentials())) {
            Student student = students.get(token.getCredentials());

            return StudentAuthenticationToken.builder()
                    .principal(student)
                    .details(student.getName())
                    .authorities(student.getRoles())
                    .authenticated(true)
                    .build();
        }

        return null;
    }

    @Override // 인증 토큰을 받을 때 인증을 수행하도록 커스텀 프로바이더를 지정한다.
    public boolean supports(Class<?> authentication) {
        // (UsernamePassword 인증 필터를 통해서 토큰을 받을 계획이다.)
        // (SecurityConfig 클래스에서 로그인 폼을 등록하였으므로 UsernamePassword 인증 필터가 동작하고,
        // UsernamePasswordAuthenticationToken을 발급한다.)

        // UsernamePasswordAuthenticationToken 클래스의 토큰 형식을 받을 경우,
        // 해당 커스텀 프로바이더로 인증 과정을 수행하도록 선언한 것이다.
        // return authentication == UsernamePasswordAuthenticationToken.class;

        // (라디오 박스 값 추가 이후)
        return authentication == StudentAuthenticationToken.class;
    }

    @Override // 맵을 초기화한다.
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Student("S1", "학생1", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
                new Student("S2", "학생2", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
                new Student("S3", "학생3", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")))
        ).forEach(set -> students.put(set.getId(), set));
    }
}