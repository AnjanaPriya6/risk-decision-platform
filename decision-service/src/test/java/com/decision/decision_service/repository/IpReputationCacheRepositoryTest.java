package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.IpReputationCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class IpReputationCacheRepositoryTest {

    @Autowired
    private IpReputationCacheRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    private IpReputationCache buildCacheEntry(String ipAddress,
                                              BigDecimal score,
                                              Instant expiresAt) {
        return IpReputationCache.builder()
                .ipAddress(ipAddress)
                .reputationScore(score)
                .source("caller_provided")
                .cachedAt(Instant.now())
                .expiresAt(expiresAt)
                .build();
    }

    // ── findByIpAddressAndExpiresAtAfter ─────────────────────────────────────

    @Test
    void find_shouldReturnEntry_whenCacheValidAndNotExpired() {
        Instant expiresAt = Instant.now().plus(24, ChronoUnit.HOURS);
        repository.save(buildCacheEntry(
                "203.0.113.10",
                new BigDecimal("0.200"),
                expiresAt));

        Optional<IpReputationCache> result = repository
                .findByIpAddressAndExpiresAtAfter("203.0.113.10", Instant.now());

        assertThat(result).isPresent();
        assertThat(result.get().getIpAddress()).isEqualTo("203.0.113.10");
        assertThat(result.get().getReputationScore())
                .isEqualByComparingTo(new BigDecimal("0.200"));
    }

    @Test
    void find_shouldReturnEmpty_whenCacheExpired() {
        // expired 1 hour ago
        Instant expiresAt = Instant.now().minus(1, ChronoUnit.HOURS);
        repository.save(buildCacheEntry(
                "203.0.113.10",
                new BigDecimal("0.200"),
                expiresAt));

        Optional<IpReputationCache> result = repository
                .findByIpAddressAndExpiresAtAfter("203.0.113.10", Instant.now());

        // expired entry should not be returned
        assertThat(result).isEmpty();
    }

    @Test
    void find_shouldReturnEmpty_whenIpNotCached() {
        Optional<IpReputationCache> result = repository
                .findByIpAddressAndExpiresAtAfter("1.2.3.4", Instant.now());

        assertThat(result).isEmpty();
    }

    @Test
    void find_shouldReturnEmpty_whenDifferentIpAddress() {
        Instant expiresAt = Instant.now().plus(24, ChronoUnit.HOURS);
        repository.save(buildCacheEntry(
                "203.0.113.10",
                new BigDecimal("0.200"),
                expiresAt));

        Optional<IpReputationCache> result = repository
                .findByIpAddressAndExpiresAtAfter("10.0.0.1", Instant.now());

        assertThat(result).isEmpty();
    }

    @Test
    void save_shouldPersistHighReputationScore() {
        Instant expiresAt = Instant.now().plus(24, ChronoUnit.HOURS);
        repository.save(buildCacheEntry(
                "1.2.3.4",
                new BigDecimal("0.950"),
                expiresAt));

        Optional<IpReputationCache> result = repository
                .findByIpAddressAndExpiresAtAfter("1.2.3.4", Instant.now());

        assertThat(result).isPresent();
        assertThat(result.get().getReputationScore())
                .isEqualByComparingTo(new BigDecimal("0.950"));
    }

    @Test
    void update_shouldRefreshExpiryTime() {
        // save with short expiry
        Instant shortExpiry = Instant.now().plus(1, ChronoUnit.HOURS);
        IpReputationCache entry = buildCacheEntry(
                "203.0.113.10",
                new BigDecimal("0.200"),
                shortExpiry);
        repository.save(entry);

        // update with new expiry
        entry.setExpiresAt(Instant.now().plus(24, ChronoUnit.HOURS));
        entry.setReputationScore(new BigDecimal("0.300"));
        repository.save(entry);

        Optional<IpReputationCache> result = repository
                .findByIpAddressAndExpiresAtAfter("203.0.113.10", Instant.now());

        assertThat(result).isPresent();
        assertThat(result.get().getReputationScore())
                .isEqualByComparingTo(new BigDecimal("0.300"));
    }
}