package com.decision.decision_service.service;

import lombok.extern.slf4j.Slf4j;
import com.decision.decision_service.model.dto.LoginAttemptRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;

@Slf4j
@Service
public class DecisionService {

    public ResponseEntity<Object> loginAttempt(LoginAttemptRequest loginAttemptRequest) {
        log.info("loginAttempt request received");
        return null;
    }
}
