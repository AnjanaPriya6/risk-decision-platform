package com.decision.decision_service.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoDto {

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$")
    private String country;

    @NotBlank
    @Size(max = 100)
    private String city;

    @Min(0)
    @Max(20000)
    private Double distanceFromLastLoginKm;

    private Boolean isNewCountry;

    @Min(0)
    private Integer minutesSinceLastLogin;
}
