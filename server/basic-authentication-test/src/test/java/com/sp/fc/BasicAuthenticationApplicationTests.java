package com.sp.fc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicAuthenticationApplicationTests {

    @LocalServerPort
    private int port;

    RestTemplate client = new RestTemplate();

    private String greetingUrl() {
        return "http://localhost:" + port + "/greeting";
    }

    @Test
    @DisplayName("1. 인증 실패")
    public void test_1() {
        // HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
        //     client.getForObject(greetingUrl(), String.class);
        // });

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> client.getForObject(greetingUrl(), String.class));

        assertEquals(401, exception.getRawStatusCode());
    }

    @Test
    @DisplayName("2-1. 인증 성공")
    public void test_2_1() {
        // 인증 헤더를 추가하기 위해 HttpHeaders 인스턴스를 생성한다.
        HttpHeaders headers = new HttpHeaders();
        // 인증 헤더에 Basic 토큰 값을 추가한다.
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " +
                Base64.getEncoder().encodeToString("user1:12345".getBytes())
        );

        // GET 방식으로 호출하므로 별도의 Body는 지정하지 않고 헤더만 넘겨 준다.
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = client.exchange(greetingUrl(), HttpMethod.GET, entity, String.class);

        assertEquals("Hello", response.getBody());
    }

    @Test
    @DisplayName("2-2. 인증 성공 (2-1 테스트와 동일한 결과)")
    public void test_2_2() {
        // TestRestTemplate 클래스는 Basic 토큰 방식을 지원한다.
        TestRestTemplate testClient = new TestRestTemplate("user1", "12345");

        String response = testClient.getForObject(greetingUrl(), String.class);

        assertEquals("Hello", response);
    }

    @Test
    @DisplayName("3. 인증 성공")
    public void test_3() {
        TestRestTemplate testClient = new TestRestTemplate("user1", "12345");

        ResponseEntity<String> response = testClient.postForEntity(greetingUrl(), "Username", String.class);

        assertEquals("Hello, Username", response.getBody());
    }
}