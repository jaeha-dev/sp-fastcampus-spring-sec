package com.sp.fc.web.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;

@Data
@NoArgsConstructor
public class Student {

    private String id; // 학번
    private String name; // 이름

    @JsonIgnore
    private Set<GrantedAuthority> roles; // 권한

    private String teacherId; // 담당 선생님

    @Builder
    public Student(String id, String name, Set<GrantedAuthority> roles, String teacherId) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.teacherId = teacherId;
    }
}