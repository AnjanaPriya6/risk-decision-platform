package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.PolicyConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class PolicyConfigRepositoryTest {

    @Autowired
    private PolicyConfigRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    private PolicyConfig buildPolicyConfig(String createdBy) {
        return PolicyConfig.builder()
                .isFirstLoginCheckActive(true)
                .bruteForceThreshold(6)
                .failedAttemptsWindowMinutes(10)
                .badIpReputationThreshold(new BigDecimal("0.950"))
                .isAccountLockedCheckActive(true)
                .impossibleTravelDistanceKm(2000)
                .impossibleTravelTimeMinutes(60)
                .trustedDeviceDowngradesBlock(true)
                .highRiskScoreThreshold(new BigDecimal("0.800"))
                .newDeviceRiskThreshold(new BigDecimal("0.650"))
                .newCountryRiskThreshold(new BigDecimal("0.600"))
                .loginVelocityThreshold(4)
                .loginVelocityWindowMinutes(10)
                .changeReason("Test policy config")
                .createdBy(createdBy)
                .build();
    }

    @Test
    void findTopByOrderByCreatedAtDesc_shouldReturnEmpty_whenNoConfig() {
        Optional<PolicyConfig> result = repository
                .findTopByOrderByCreatedAtDesc();

        assertThat(result).isEmpty();
    }

    @Test
    void findTopByOrderByCreatedAtDesc_shouldReturnConfig_whenOneExists() {
        repository.save(buildPolicyConfig("system"));

        Optional<PolicyConfig> result = repository
                .findTopByOrderByCreatedAtDesc();

        assertThat(result).isPresent();
        assertThat(result.get().getCreatedBy()).isEqualTo("system");
    }

    @Test
    void findTopByOrderByCreatedAtDesc_shouldReturnMostRecent_whenMultipleExist()
            throws InterruptedException {
        repository.save(buildPolicyConfig("system"));
        Thread.sleep(10); // ensure different timestamps
        repository.save(buildPolicyConfig("risk-team"));

        Optional<PolicyConfig> result = repository
                .findTopByOrderByCreatedAtDesc();

        assertThat(result).isPresent();
        // most recently inserted should be returned
        assertThat(result.get().getCreatedBy()).isEqualTo("risk-team");
    }

    @Test
    void save_shouldPersistAllThresholds() {
        PolicyConfig saved = repository.save(buildPolicyConfig("system"));

        Optional<PolicyConfig> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getBruteForceThreshold()).isEqualTo(6);
        assertThat(found.get().getBadIpReputationThreshold())
                .isEqualByComparingTo(new BigDecimal("0.950"));
        assertThat(found.get().getHighRiskScoreThreshold())
                .isEqualByComparingTo(new BigDecimal("0.800"));
        assertThat(found.get().getImpossibleTravelDistanceKm()).isEqualTo(2000);
        assertThat(found.get().getTrustedDeviceDowngradesBlock()).isTrue();
    }
}