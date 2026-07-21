package com.decision.decision_service.model.dto;

import com.decision.decision_service.model.enums.StepUpResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepUpResultRequest {

    @NotBlank(message = "CorrelationId cannot be null")
    private String correlationId;

    @NotBlank(message = "userId cannot be null")
    private String userId;

    @NotBlank(message = "deviceId cannot be null")
    private String deviceId;

    @NotBlank(message = "country cannot be null")
    @Size(min = 2, max = 2, message = "must be two-lettered country code")
    private String country;

    private String city;

    @NotBlank(message = "ip_address is required")
    @JsonProperty("ip_address")
    private String ipAddress;

    @NotNull
    private StepUpResult result;

    @NotNull
    private Instant completedAt;

}
