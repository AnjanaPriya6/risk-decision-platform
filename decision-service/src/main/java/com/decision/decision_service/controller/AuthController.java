package com.decision.decision_service.controller;

import com.decision.decision_service.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JWTUtil jwtUtil;

    @Value("${test.client.id}")
    private String testClientId;

    @Value("${test.client.secret}")
    private String testClientSecret;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody Map<String, String> request) {
        String clientId = request.get("client_id");
        String clientSecret = request.get("client_secret");
        if (clientId == null || clientSecret == null
                || !clientId.equals(testClientId)
                || !clientSecret.equals(testClientSecret)) {

            return ResponseEntity
                    .status(401)
                    .body(Map.of(
                            "error_code", "UNAUTHORIZED",
                            "message", "Invalid credentials"
                    ));
        }
        String token = jwtUtil.generateToken(clientId);

        return ResponseEntity.ok(Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "expires_in", 3600
        ));
    }
}
