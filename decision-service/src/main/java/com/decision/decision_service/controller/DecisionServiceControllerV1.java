package com.decision.decision_service.controller;

import com.decision.decision_service.service.PolicyConfigService;
import jakarta.validation.Valid;
import com.decision.decision_service.model.dto.LoginAttemptRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.decision.decision_service.service.DecisionService;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/v1/decisions")
public class DecisionServiceControllerV1 {

    @Autowired
    private DecisionService decisionService;

    @Autowired
    private PolicyConfigService policyConfigService;

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

    @GetMapping("/v1/policies")
    public ResponseEntity<Object> policyLoad(){
            return policyConfigService.loadCurrentConfig();
        }
}