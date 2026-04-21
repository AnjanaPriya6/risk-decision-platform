package com.decision.decision_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@Order(1)
public class CorrealationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    // this key must match what you configure in logback-spring.xml
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";
    // prefix for system-generated IDs
    // makes it easy to distinguish generated vs caller-provided IDs in logs
    private static final String GENERATED_ID_PREFIX = "c-";\

    @Override
    public void doFilterInternal()


}
