package com.decision.decision_service.model.dto;

import com.decision.decision_service.model.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecordResponse {

    private UUID id;

    @JsonProperty("correlation_id")
    private String correlationId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("event_type")
    private EventType eventType;

    private DecisionOutcome decision;

    @JsonProperty("reason_codes")
    private List<ReasonCode> reasonCodes;

    @JsonProperty("risk_score")
    private BigDecimal riskScore;

    @JsonProperty("policy_version")
    private String policyVersion;

    @JsonProperty("model_version")
    private String modelVersion;

    @JsonProperty("decision_time_ms")
    private Integer decisionTimeMs;

    private Channel channel;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("step_up_outcome")
    private StepUpResult stepUpOutcome;

    @JsonProperty("step_up_completed_at")
    private Instant stepUpCompletedAt;

    @JsonProperty("created_at")
    private Instant createdAt;

}
