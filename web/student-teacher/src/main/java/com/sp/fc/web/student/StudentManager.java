package com.sp.fc.web.student;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    // 현재 DB 연동을 하지 않으므로 가상의 데이터를 사용한다고 가정한다.
    private final Map<String, Student> students = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;

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
        return authentication == StudentAuthenticationToken.class;
    }

    public List<Student> getStudents(String teacherId) {
        // teacherId가 담당하는 학생 목록을 반환한다.
        return students.values()
                .stream()
                .filter(s -> s.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    @Override // 맵을 초기화한다.
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Student("S1", "학생1", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "T1"),
                new Student("S2", "학생2", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "T1"),
                new Student("S3", "학생3", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "T1")
        ).forEach(set -> students.put(set.getId(), set));
    }
}