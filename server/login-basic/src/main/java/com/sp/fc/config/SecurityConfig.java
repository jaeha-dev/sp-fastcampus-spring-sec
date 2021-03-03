package com.sp.fc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
// @EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomAuthDetails customAuthDetails;

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
                        .roles("ADMIN"));
                        // .roles("USER", "ADMIN"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
        );
        http.formLogin(login -> login
                // 로그인 웹 페이지를 지정하고 접근 권한을 모두 허용한다.
                // LoginForm.html 파일에서 Form 태그에 CSRF 토큰 옵션을 지정해야 한다.
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", false)
                .failureUrl("/login-error")
                .authenticationDetailsSource(customAuthDetails)
        );
        http.logout(logout -> logout
                .logoutSuccessUrl("/")
        );
        http.exceptionHandling(exception -> exception
                .accessDeniedPage("/access-denied")
        );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 웹 리소스에 대한 접근을 허용한다. (/css, /js, /images, /webjars, /favicon)
        web.ignoring().requestMatchers(PathRequest
                .toStaticResources()
                .atCommonLocations()
        );
    }

    @Bean
    protected RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}