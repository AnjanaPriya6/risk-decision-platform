package com.decision.decision_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoftDeleteResponseDto {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("deleted_at")
    private Instant deletedAt;

}
