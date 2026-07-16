package com.decision.decision_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "ip_reputation_cache")
public class IpReputationCache {

    @Id
    @Column(name="ip_address",updatable = false,nullable = false)
    private String ipAddress;

    @Column(name = "reputation_score", nullable = false, columnDefinition = "numeric")
    private BigDecimal reputationScore;

    @Column(name = "source", nullable = false, updatable = false)
    private String source;

    @Column(name = "cached_at", nullable = false)
    private Instant cachedAt;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private Instant expiresAt;
}
