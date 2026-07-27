package com.decision.decision_service.exception;

public class PolicyConfigNotFoundException extends RuntimeException {
    public PolicyConfigNotFoundException(String message) {
        super(message);
    }
}
