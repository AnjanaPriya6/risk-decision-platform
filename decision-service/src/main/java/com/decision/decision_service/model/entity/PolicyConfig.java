package com.decision.decision_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PolicyConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "policy_version", nullable = false, updatable = false,unique = true, insertable = false)
    private String policyVersion; //updated by the trigger interally in the db

    @Column(name = "is_first_login_check_active", nullable = false)
    private Boolean isFirstLoginCheckActive;

    @Column(name = "brute_force_threshold", nullable = false)
    private int bruteForceThreshold;

    @Column(name = "failed_attempts_window_minutes", nullable = false)
    private int failedAttemptsWindowMinutes;

    @Column(name = "bad_ip_reputation_threshold", nullable = false)
    private double badIpReputationThreshold;

    @Column(name = "is_account_locked_check_active", nullable = false)
    private Boolean isAccountLockedCheckActive;

    @Column(name = "impossible_travel_distance_km", nullable = false)
    private Integer impossibleTravelDistanceKm;

    @Column(name = "impossible_travel_time_minutes", nullable = false)
    private Integer impossibleTravelTimeMinutes;

    @Column(name = "trusted_device_downgrades_block", nullable = false)
    private Boolean trustedDeviceDowngradesBlock;

    @Column(name = "high_risk_score_threshold", nullable = false)
    private double highRiskScoreThreshold;

    @Column(name = "new_device_risk_threshold", nullable = false)
    private double newDeviceRiskThreshold;

    @Column(name = "new_country_risk_threshold", nullable = false)
    private double newCountryRiskThreshold;

    @Column(name = "login_velocity_threshold", nullable = false)
    private Integer loginVelocityThreshold;

    @Column(name = "login_velocity_window_minutes", nullable = false)
    private Integer loginVelocityWindowMinutes;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;
}
