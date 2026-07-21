package com.decision.decision_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyRefreshResponse {

    private String message;

    @JsonProperty("policy_version")
    private String policyVersion;

    @JsonProperty("refreshed_at")
    private Instant refreshedAt;

}
