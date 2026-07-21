package com.decision.decision_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponse {

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("first_seen")
    private Instant firstSeen;

    @JsonProperty("last_seen")
    private Instant lastSeen;

    @JsonProperty("login_count")
    private Integer loginCount;

    @JsonProperty("is_trusted")
    private Boolean isTrusted;

    @JsonProperty("trusted_at")
    private Instant trustedAt;

    @JsonProperty("trust_revoked_at")
    private Instant trustRevokedAt;

    @JsonProperty("deleted_at")
    private Instant deletedAt;

}
