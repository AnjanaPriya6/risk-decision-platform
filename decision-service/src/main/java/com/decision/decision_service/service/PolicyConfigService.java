package com.decision.decision_service.service;
import com.decision.decision_service.exception.PolicyConfigNotFoundException;
import com.decision.decision_service.model.dto.Flags;
import com.decision.decision_service.model.dto.PolicyConfigResponse;
import com.decision.decision_service.model.dto.PolicyRefreshResponse;
import com.decision.decision_service.model.dto.Thresholds;
import com.decision.decision_service.model.entity.PolicyConfig;
import com.decision.decision_service.repository.PolicyConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@AllArgsConstructor
public class PolicyConfigService {

    private final PolicyConfigRepository policyConfigRepository;

    @Cacheable("policyConfig")
    public PolicyConfig loadCurrentConfig(){
        return policyConfigRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new PolicyConfigNotFoundException("no policy found in cache"));
    }

    @CacheEvict(value = "policyConfig", allEntries = true)
    public PolicyRefreshResponse refreshCache(){
        PolicyConfig fresh = policyConfigRepository
                .findTopByOrderByCreatedAtDesc()
                .orElseThrow();

        return PolicyRefreshResponse.builder()
                .message("Policy cache refreshed successfully")
                .policyVersion(fresh.getPolicyVersion())
                .refreshedAt(Instant.now())
                .build();
    }

    public PolicyConfigResponse toResponse(PolicyConfig config){
        return PolicyConfigResponse.builder()
                .policyVersion(config.getPolicyVersion())
                .createdAt(Instant.now())
                .createdBy(config.getCreatedBy())
                .changeReason(config.getChangeReason())
                .flags(Flags.builder()
                        .isFirstLoginCheckActive(config.getIsFirstLoginCheckActive())
                        .isAccountLockedCheckActive(config.getIsAccountLockedCheckActive())
                        .trustedDeviceDowngradesBlock(config.getTrustedDeviceDowngradesBlock())
                        .build()
                )
                .thresholds(Thresholds.builder()
                        .bruteForceThreshold(config.getBruteForceThreshold())
                        .failedAttemptsWindowMinutes(config.getFailedAttemptsWindowMinutes())
                        .badIpReputationThreshold(config.getBadIpReputationThreshold())
                        .impossibleTravelDistanceKm(config.getImpossibleTravelDistanceKm())
                        .impossibleTravelTimeMinutes(config.getImpossibleTravelTimeMinutes())
                        .highRiskScoreThreshold(config.getHighRiskScoreThreshold())
                        .newDeviceRiskThreshold(config.getNewDeviceRiskThreshold())
                        .newCountryRiskThreshold(config.getNewCountryRiskThreshold())
                        .loginVelocityThreshold(config.getLoginVelocityThreshold())
                        .loginVelocityWindowMinutes(config.getLoginVelocityWindowMinutes())
                        .build())
                .build();
    }


}
