package com.decision.decision_service.model.dto;

import com.decision.decision_service.model.enums.DecisionOutcome;
import com.decision.decision_service.model.enums.ReasonCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecisionResponse {

    @JsonProperty("correlation_id")
    private String correlationId;

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
}
