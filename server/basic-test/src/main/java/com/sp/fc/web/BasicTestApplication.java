package com.sp.fc.web;

import com.sp.fc.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasicTestApplication {

    public static void main(String[] args) {
        // build.gradle 파일에서 하위 프로젝트를 참조하도록 명시해야 사용할 수 있다.
        // Person person = Person.builder()
        //         .name("test")
        //         .build();

        SpringApplication.run(BasicTestApplication.class, args);
    }
}