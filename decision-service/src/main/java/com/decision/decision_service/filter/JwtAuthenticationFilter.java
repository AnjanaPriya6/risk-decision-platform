package com.decision.decision_service.filter;

import com.decision.decision_service.util.CorrelationIdHolder;
import com.decision.decision_service.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(BEARER_PREFIX.length());
        if (!jwtUtil.isTokenValid(token)) {
            sendUnauthorizedResponse(response, "JWT token is invalid or expired");
            return;
        }
        String subject = jwtUtil.extractSubject(token);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        subject,    // principal — who is authenticated
                        null,       // credentials — null because JWT is already verified
                        List.of()   // authorities — empty list, no role-based auth in this project
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("JWT authenticated — subject={}", subject);
        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // read from MDC
        String correlationId = CorrelationIdHolder.get();

        // explicitly set the header on the response
        // CorrelationIdFilter sets this too but may not be committed yet
        response.setHeader("X-Correlation-Id", correlationId);

        String body = String.format("""
            {
              "correlation_id": "%s",
              "error_code": "UNAUTHORIZED",
              "message": "%s",
              "timestamp": "%s"
            }
            """,
                correlationId,
                message,
                java.time.Instant.now().toString()
        );

        response.getWriter().write(body);
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

}
