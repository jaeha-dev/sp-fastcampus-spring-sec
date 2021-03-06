package com.sp.fc.web.config;

import com.sp.fc.web.student.StudentManager;
import com.sp.fc.web.teacher.TeacherManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final StudentManager studentManager;
    private final TeacherManager teacherManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 리스트로 관리하므로 여러 개의 인증 프로바이더를 지정할 수 있다.
        auth.authenticationProvider(studentManager);
        auth.authenticationProvider(teacherManager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // http.authorizeRequests(request -> request
        //         .antMatchers("/").permitAll()
        //         .anyRequest().authenticated()
        // );

        // 로그인 폼을 통해서 UsernamePassword 인증 필터가 동작한다.
        // http.formLogin(login -> login
        //         .loginPage("/login").permitAll()
        //         .defaultSuccessUrl("/", false)
        // );

        // 커스터마이징한 로그인 필터를 사용하기 위해 /login 접근도 허용한다.
        http.authorizeRequests(request -> request
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
        );

        // 커스터마이징한 로그인 필터를 생성하고 UsernamePassword 인증 필터를 대체한다.
        // (이 경우에 로그인 성공/실패에 대한 핸들러 클래스를 추가해야 한다.)
        CustomLoginFilter loginFilter = new CustomLoginFilter(authenticationManager());
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout(logout -> logout
                .logoutSuccessUrl("/")
        );

        http.exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 웹 리소스에 대한 접근을 허용한다. (/css, /js, /images, /webjars, /favicon)
        web.ignoring().requestMatchers(PathRequest
                .toStaticResources()
                .atCommonLocations()
        );
    }
}