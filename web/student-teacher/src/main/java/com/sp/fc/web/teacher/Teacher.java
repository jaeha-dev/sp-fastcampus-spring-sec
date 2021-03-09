package com.sp.fc.web.teacher;

import com.sp.fc.web.student.Student;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Teacher {

    private String id; // 교번
    private String name; // 이름
    private Set<GrantedAuthority> roles; // 권한
    private List<Student> students; // 담당 학생

    @Builder
    public Teacher(String id, String name, Set<GrantedAuthority> roles, List<Student> students) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.students = students;
    }
}