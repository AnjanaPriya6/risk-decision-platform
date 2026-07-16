package com.decision.decision_service.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.decision.decision_service.model.enums.Channel;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextDto {
    @NotNull
    private Instant timestamp;

    @NotNull
    private Channel channel;

    @NotBlank
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
    private String ipAddress;

    @Size(max = 500)
    private String userAgent;

    @Valid
    @NotNull(message = "DeviceDto cannot be null")
    private DeviceDto device;

    @Valid
    @NotNull(message = "GeoDto cannot be null")
    private GeoDto geo;


}
