package com.decision.decision_service.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dependencies {

    private DatabaseStatus database;

    @JsonProperty("risk_service")
    private RiskServiceStatus riskService;

    @JsonProperty("policy_cache")
    private PolicyCache policyCache;
}
