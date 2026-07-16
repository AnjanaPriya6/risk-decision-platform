package com.decision.decision_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    // this key must match what you configure in logback-spring.xml
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";
    // prefix for system-generated IDs
    private static final String GENERATED_ID_PREFIX = "c-";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = extractOrGenerate(request);

        try {
            MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
            response.setHeader(CORRELATION_ID_HEADER, correlationId);

            filterChain.doFilter(request, response);

        } finally {
            MDC.clear();
        }
    }

    private String extractOrGenerate(HttpServletRequest request) {
        String header = request.getHeader(CORRELATION_ID_HEADER);

        // if caller provided a correlation ID, use it as-is
        if (header != null && !header.isBlank()) {
            return header;
        }

        // caller did not provide one, generate a new UUID
        // prefix with c- to identify system generated IDs in logs
        return GENERATED_ID_PREFIX + UUID.randomUUID();
    }
}