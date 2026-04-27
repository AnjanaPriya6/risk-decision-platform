package com.decision.decision_service.config;

import com.decision.decision_service.filter.CorrelationIdFilter;
import com.decision.decision_service.filter.JwtAuthenticationFilter;
import com.decision.decision_service.util.CorrelationIdHolder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/health").permitAll()
                        .requestMatchers("/auth/token").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");

                            String correlationId = response.getHeader("X-Correlation-Id");
                            if (correlationId == null || correlationId.isBlank()) {
                                correlationId = CorrelationIdHolder.get();
                            }
                            if (correlationId == null || correlationId.isBlank()) {
                                correlationId = "UNKNOWN";
                            }

                            response.setHeader("X-Correlation-Id", correlationId);

                            String body = String.format("""
                    {
                      "correlation_id": "%s",
                      "error_code": "UNAUTHORIZED",
                      "message": "Authentication required",
                      "timestamp": "%s"
                    }
                    """,
                                    correlationId,
                                    java.time.Instant.now().toString()
                            );

                            response.getWriter().write(body);
                        })
                )
                .addFilterAfter(
                        new CorrelationIdFilter(),
                        SecurityContextHolderFilter.class
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
