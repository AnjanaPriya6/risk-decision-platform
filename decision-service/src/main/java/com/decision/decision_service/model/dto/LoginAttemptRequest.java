package com.decision.decision_service.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.decision.decision_service.model.enums.EventType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAttemptRequest {

    @NotNull(message = "EventType cannot be null")
    private EventType eventType;

    @Valid
    @NotNull(message = "UserDto cannot be null")
    private UserDto user;

    @Valid
    @NotNull(message = "contextDto cannot be null")
    private ContextDto context;

    @Valid
    @NotNull(message = "SignalDto cannot be null")
    private SignalDto signal;

}
