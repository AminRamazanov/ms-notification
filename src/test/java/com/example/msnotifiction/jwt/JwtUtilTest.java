package com.example.msnotifiction.jwt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtUtilTest {

    @Test
    void shouldCreateJwtUtil() {
        JwtUtil jwtUtil = new JwtUtil();
        assertNotNull(jwtUtil);
    }
}