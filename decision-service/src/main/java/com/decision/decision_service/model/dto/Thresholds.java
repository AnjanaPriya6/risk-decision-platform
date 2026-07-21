package com.decision.decision_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Thresholds {

    @JsonProperty("brute_force_threshold")
    private Integer bruteForceThreshold;

    @JsonProperty("failed_attempts_window_minutes")
    private Integer failedAttemptsWindowMinutes;

    @JsonProperty("bad_ip_reputation_threshold")
    private BigDecimal badIpReputationThreshold;

    @JsonProperty("impossible_travel_distance_km")
    private Integer impossibleTravelDistanceKm;

    @JsonProperty("impossible_travel_time_minutes")
    private Integer impossibleTravelTimeMinutes;

    @JsonProperty("high_risk_score_threshold")
    private BigDecimal highRiskScoreThreshold;

    @JsonProperty("new_device_risk_threshold")
    private BigDecimal newDeviceRiskThreshold;

    @JsonProperty("new_country_risk_threshold")
    private BigDecimal newCountryRiskThreshold;

    @JsonProperty("login_velocity_threshold")
    private Integer loginVelocityThreshold;

    @JsonProperty("login_velocity_window_minutes")
    private Integer loginVelocityWindowMinutes;

}
