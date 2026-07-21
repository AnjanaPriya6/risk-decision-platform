package com.decision.decision_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flags {

    @JsonProperty("is_first_login_check_active")
    private Boolean isFirstLoginCheckActive;

    @JsonProperty("is_account_locked_check_active")
    private Boolean isAccountLockedCheckActive;

    @JsonProperty("trusted_device_downgrades_block")
    private Boolean trustedDeviceDowngradesBlock;

}
