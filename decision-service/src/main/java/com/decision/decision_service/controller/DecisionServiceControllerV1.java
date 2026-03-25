package com.decision.decision_service.controller;

import jakarta.validation.Valid;
import com.decision.decision_service.model.dto.LoginAttemptRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.decision.decision_service.service.DecisionService;

@RestController
@RequestMapping("/v1/decisions")
public class DecisionServiceControllerV1 {

    @Autowired
    private DecisionService decisionService;

    @PostMapping("/login-attempt")
    public ResponseEntity<Object> loginAttempt(
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @Valid @RequestBody LoginAttemptRequest loginAttemptRequest) {
        return decisionService.loginAttempt(loginAttemptRequest);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}