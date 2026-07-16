package com.decision.decision_service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private JWTUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JWTUtil();
        // inject test values directly — no Spring context needed
        ReflectionTestUtils.setField(jwtUtil, "secret",
                "test-secret-key-that-is-at-least-32-characters-long");
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 3600000L);
    }

    @Test
    void generateToken_shouldReturnValidJwtString() {
        String token = jwtUtil.generateToken("test-client-01");

        // JWT has three parts separated by dots
        assertThat(token).isNotNull();
        assertThat(token.split("\\.")).hasSize(3);
    }

    @Test
    void isTokenValid_shouldReturnTrue_forValidToken() {
        String token = jwtUtil.generateToken("test-client-01");

        assertThat(jwtUtil.isTokenValid(token)).isTrue();
    }

    @Test
    void isTokenValid_shouldReturnFalse_forInvalidToken() {
        assertThat(jwtUtil.isTokenValid("invalid.token.string")).isFalse();
    }

    @Test
    void isTokenValid_shouldReturnFalse_forNullToken() {
        assertThat(jwtUtil.isTokenValid(null)).isFalse();
    }

    @Test
    void isTokenValid_shouldReturnFalse_forBlankToken() {
        assertThat(jwtUtil.isTokenValid("")).isFalse();
    }

    @Test
    void isTokenValid_shouldReturnFalse_forExpiredToken() {
        // create a util with 0ms expiration — token expires immediately
        JWTUtil expiredUtil = new JWTUtil();
        ReflectionTestUtils.setField(expiredUtil, "secret",
                "test-secret-key-that-is-at-least-32-characters-long");
        ReflectionTestUtils.setField(expiredUtil, "expirationMs", 0L);

        String expiredToken = expiredUtil.generateToken("test-client-01");

        assertThat(jwtUtil.isTokenValid(expiredToken)).isFalse();
    }

    @Test
    void isTokenValid_shouldReturnFalse_forTamperedToken() {
        String token = jwtUtil.generateToken("test-client-01");

        // tamper with the payload section (middle part)
        String[] parts = token.split("\\.");
        String tamperedToken = parts[0] + ".tampered_payload." + parts[2];

        assertThat(jwtUtil.isTokenValid(tamperedToken)).isFalse();
    }

    @Test
    void extractSubject_shouldReturnCorrectSubject() {
        String token = jwtUtil.generateToken("test-client-01");

        assertThat(jwtUtil.extractSubject(token)).isEqualTo("test-client-01");
    }

    @Test
    void generateToken_differentSubjects_shouldProduceDifferentTokens() {
        String token1 = jwtUtil.generateToken("client-01");
        String token2 = jwtUtil.generateToken("client-02");

        assertThat(token1).isNotEqualTo(token2);
    }
}
