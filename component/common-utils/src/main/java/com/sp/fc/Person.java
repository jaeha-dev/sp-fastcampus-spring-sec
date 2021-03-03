package com.sp.fc;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person {

    private String name;

    @Builder
    public Person(String name) {
        this.name = name;
    }
}