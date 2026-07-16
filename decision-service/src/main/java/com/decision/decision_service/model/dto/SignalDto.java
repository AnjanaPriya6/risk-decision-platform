package com.decision.decision_service.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignalDto {

    @Min(0)
    @Max(100)
    private Integer failedAttemptsLast10m;

    @Min(0)
    @Max(100)
    private Integer loginAttemptsLast10m;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private BigDecimal ipReputation;

    private Boolean accountLocked;
}
