package com.sp.fc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello";
    }

    @PostMapping("/greeting")
    public String greeting(@RequestBody String name) {
        return "Hello, " + name;
    }
}