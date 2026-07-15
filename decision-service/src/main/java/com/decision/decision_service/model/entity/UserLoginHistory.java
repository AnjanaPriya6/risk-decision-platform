package com.decision.decision_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "user_login_history")
public class UserLoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "country", nullable = false, updatable = false)
    private String country;

    @Column(name = "city", updatable = false)
    private String city;

    @Column(name = "ip_address", updatable = false)
    private String ipAddress;

    @Column(name = "device_id",updatable = false)
    private String deviceId;

    @Column(name = "was_successful", nullable = false, updatable = false)
    private Boolean wasSuccessful;

    @Column(name = "logged_in_at", nullable = false, updatable = false, insertable = false)
    private Instant loggedInAt;
}
