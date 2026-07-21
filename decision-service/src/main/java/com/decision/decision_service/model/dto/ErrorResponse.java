package com.decision.decision_service.model.dto;

import com.decision.decision_service.model.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @JsonProperty("correlation_id")
    private String correlationId;

    @JsonProperty("error_code")
    private ErrorCode errorCode;

    private String message;

    @JsonProperty("field_errors")
    private List<FieldError> fieldError;

    private Instant timestamp;

}
