package com.decision.decision_service.model.dto;

import com.decision.decision_service.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyCache {

    private Status status;

    @JsonProperty("policy_version")
    private String policyVersion;

    @JsonProperty("policies_loaded")
    private Integer policiesLoaded;
}
