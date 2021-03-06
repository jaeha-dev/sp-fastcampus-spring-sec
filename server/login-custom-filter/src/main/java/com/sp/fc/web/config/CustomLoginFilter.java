package com.sp.fc.web.config;

import com.sp.fc.web.student.StudentAuthenticationToken;
import com.sp.fc.web.teacher.TeacherAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 필터 커스터마이징
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    // 생성자 주입
    public CustomLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // attemptAuthentication() 메서드의 구현부를 가져와서 오버라이딩한다.
        // if (this.postOnly && !request.getMethod().equals("POST")) {
        //     throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        // }

        // ID (공백 제거)
        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();

        // PW (공백 제거)
        String password = obtainPassword(request);
        password = (password != null) ? password : "";

        // (라디오 박스 값 추가 이전) ID, PW를 사용하여 토큰을 생성하고 전달한다.
        // UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // return this.getAuthenticationManager().authenticate(authRequest);

        // 라디오 박스 값 (학생 or 선생님)
        String type = request.getParameter("type");
        if (type == null || ! type.equals("teacher")) {
            // student
            StudentAuthenticationToken token = StudentAuthenticationToken.builder()
                    .credentials(username) // 편의상, username(ID)를 등록한다.
                    .build();
            return this.getAuthenticationManager().authenticate(token);
        } else {
            // teacher
            TeacherAuthenticationToken token = TeacherAuthenticationToken.builder()
                    .credentials(username) // 편의상, username(ID)를 등록한다.
                    .build();
            return this.getAuthenticationManager().authenticate(token);
        }
    }
}