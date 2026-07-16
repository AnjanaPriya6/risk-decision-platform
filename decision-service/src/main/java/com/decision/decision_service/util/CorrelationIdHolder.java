package com.decision.decision_service.util;

import org.slf4j.MDC;

public class CorrelationIdHolder {

    private static final String KEY = "correlationId";

    // read the current correlation ID from MDC or fallback to UNKOWN
    public static String get() {
        String id = MDC.get(KEY);
        return id != null ? id : "UNKNOWN";
    }

    private CorrelationIdHolder() {
        //private constructor to not let anyone create an unnecessary instance of this class
    }
}
