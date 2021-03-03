package com.sp.fc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTestApplicationTests {

    @Test
    @DisplayName("1. 부트스트랩 클래스 테스트")
    public void test1() {
        assertEquals("test", "test");
    }
}