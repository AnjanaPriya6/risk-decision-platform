package com.decision.decision_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyConfigResponse {

    @JsonProperty("policy_version")
    private String policyVersion;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("change_reason")
    private String changeReason;

    private Flags flags;

    private Thresholds thresholds;

}
