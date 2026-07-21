package com.decision.decision_service.model.dto;

import com.decision.decision_service.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskServiceStatus {

    private Status status;

    @JsonProperty("response_time_ms")
    private Integer responseTimeMs;

    private String error;
}
