package com.sp.fc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Order(1) // 필터 체인을 여러 개 구성할 경우, 순서를 지정해야 한다.
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("12345"))
                        .roles("USER"))
                .withUser(User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("12345"))
                        .roles("USER", "ADMIN"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 시큐리티 필터 체인을 구성한다.
        http.authorizeRequests((requests) -> requests
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated()
        );
        // 폼 로그인 방식으로 로그인 웹 페이지, 로그인 성공/실패 핸들러 등을 구성할 수 있다.
        // http.formLogin((login) -> login
        //         .loginPage("")
        //         // .defaultSuccessUrl("/", false)
        //         .successHandler(null)
        //         .failureHandler(null)
        // );
        // 로그아웃 웹 페이지를 구성할 수 있다.
        // http.logout((logout) -> logout
        //         .logoutSuccessHandler(null)
        //         .logoutSuccessUrl("/")
        // );
        http.httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}