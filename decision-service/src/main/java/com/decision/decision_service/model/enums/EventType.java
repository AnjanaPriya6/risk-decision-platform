package com.decision.decision_service.model.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EventType {

    LOGIN_ATTEMPT,
    PAYMENT_INITIATION;

    @JsonCreator
    public static EventType fromValue(String value) {
        return EventType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
