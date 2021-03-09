package com.sp.fc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.fc.web.student.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.List;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiChainProxyTests {

    @LocalServerPort
    private int port;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    @DisplayName("1. 학생 수 조회")
    public void test_1() throws JsonProcessingException {
        String url = format("http://localhost:%d/api/teacher/students", port);

        // 헤더 생성 및 Basic 토큰 값 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " +
                Base64.getEncoder().encodeToString("T1:1".getBytes()));

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        List<Student> students = new ObjectMapper().readValue(response.getBody(),
                new TypeReference<>() {
                });

        System.out.println(students);
        assertEquals(3, students.size());
    }

    @Test
    @DisplayName("2. 학생 수 조회")
    public void test_2() {
        String url = "http://localhost:" + port + "/api/teacher/students";

        TestRestTemplate testRestTemplate = new TestRestTemplate("T1", "1");
        ResponseEntity<List<Student>> response = testRestTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
        });
        System.out.println(response);

        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }
}