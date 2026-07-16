package com.decision.decision_service.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Channel {
    MOBILE,
    WEB,
    API,
    ATM,
    BRANCH;

    @JsonCreator
    public static Channel fromValue(String value) {
        return Channel.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
