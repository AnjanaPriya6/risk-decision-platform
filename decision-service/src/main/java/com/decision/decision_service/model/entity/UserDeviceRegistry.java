package com.decision.decision_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "user_device_registry")
@Builder
public class UserDeviceRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "device_id", nullable = false, updatable = false)
    private String deviceId;

    @Column(name = "first_seen", nullable = false, updatable = false)
    private Instant firstSeen;

    @Column(name = "last_seen", nullable = false)
    private Instant lastSeen;

    @Column(name = "login_count", nullable = false)
    private Integer loginCount;

    @Column(name = "is_trusted", nullable = false)
    private Boolean isTrusted;

    @Column(name = "trusted_at")
    private Instant trustedAt;

    @Column(name = "trust_revoked_at")
    private Instant trustRevokedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
