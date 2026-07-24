package com.decision.decision_service.controller;

import com.decision.decision_service.model.dto.PolicyConfigResponse;
import com.decision.decision_service.model.dto.PolicyRefreshResponse;
import com.decision.decision_service.model.entity.PolicyConfig;
import com.decision.decision_service.service.PolicyConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/policies")
@RequiredArgsConstructor
public class PolicyControllerV1 {

    private final PolicyConfigService policyConfigService;

    @GetMapping
    public ResponseEntity<PolicyConfigResponse> policyLoad(){
        PolicyConfig config = policyConfigService.loadCurrentConfig();
        return ResponseEntity.ok(policyConfigService.toResponse(config));
    }

    @PostMapping("/refresh")
    public ResponseEntity<PolicyRefreshResponse> refreshPolicy() {
        PolicyRefreshResponse response = policyConfigService.refreshCache();
        return ResponseEntity.ok(response);
    }
}
