package com.decision.decision_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "user_device_registry")
public class UserDeviceRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "device_id", nullable = false, updatable = false)
    private String deviceId;

    @Column(name = "first_seen", nullable = false, insertable = false, updatable = false)
    private Instant firstSeen;

    @Column(name = "last_seen", nullable = false, insertable = false, updatable = false)
    private Instant lastSeen;

    @Column(name = "login_count", nullable = false)
    private Integer loginCount;

    @Column(name = "is_trusted", nullable = false)
    private Boolean isTrusted;

    @Column(name = "trusted_at")
    private Instant trustedAt;

    @Column(name = "trusted_revoked_at")
    private Instant trustedRevokedAt;

    @Column(name = "deleted_at")
    private Instant DeletedAt;
}
