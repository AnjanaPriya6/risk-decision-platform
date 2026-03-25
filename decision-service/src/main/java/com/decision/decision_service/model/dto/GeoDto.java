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
    private Double distance_from_last_login_km;

    @NotNull
    private Boolean is_new_country;
}
