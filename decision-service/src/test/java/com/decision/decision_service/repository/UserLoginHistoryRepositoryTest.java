package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.UserLoginHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class UserLoginHistoryRepositoryTest {

    @Autowired
    private UserLoginHistoryRepository repository;

    // clean up after each test so tests do not interfere with each other
    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    // ── HELPER ────────────────────────────────────────────────────────────────
    private UserLoginHistory buildEntry(String userId, String country,
                                        boolean wasSuccessful) {
        return UserLoginHistory.builder()
                .userId(userId)
                .country(country)
                .city("Singapore")
                .ipAddress("203.0.113.10")
                .deviceId("device_abc")
                .wasSuccessful(wasSuccessful)
                .build();
    }

    // ── P0 — FIRST LOGIN ──────────────────────────────────────────────────────
    @Test
    void existsByUserIdAndWasSuccessfulTrue_shouldReturnFalse_whenNoHistory() {
        boolean exists = repository.existsByUserIdAndWasSuccessfulTrue("U_NEW");
        assertThat(exists).isFalse();
    }

    @Test
    void existsByUserIdAndWasSuccessfulTrue_shouldReturnTrue_whenSuccessfulLoginExists() {
        repository.save(buildEntry("U123", "SG", true));

        boolean exists = repository.existsByUserIdAndWasSuccessfulTrue("U123");
        assertThat(exists).isTrue();
    }

    @Test
    void existsByUserIdAndWasSuccessfulTrue_shouldReturnFalse_whenOnlyFailedLogins() {
        repository.save(buildEntry("U123", "SG", false));

        boolean exists = repository.existsByUserIdAndWasSuccessfulTrue("U123");
        assertThat(exists).isFalse();
    }

    // ── P1 — BRUTE FORCE ──────────────────────────────────────────────────────
    @Test
    void countFailedAttempts_shouldReturnCorrectCount() {
        Instant tenMinutesAgo = Instant.now().minus(10, ChronoUnit.MINUTES);

        // two failed attempts
        repository.save(buildEntry("U123", "SG", false));
        repository.save(buildEntry("U123", "SG", false));
        // one successful — should not be counted
        repository.save(buildEntry("U123", "SG", true));

        int count = repository
                .countByUserIdAndWasSuccessfulFalseAndLoggedInAtAfter(
                        "U123", tenMinutesAgo);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void countFailedAttempts_shouldReturnZero_whenNoFailedAttempts() {
        repository.save(buildEntry("U123", "SG", true));

        int count = repository
                .countByUserIdAndWasSuccessfulFalseAndLoggedInAtAfter(
                        "U123", Instant.now().minus(10, ChronoUnit.MINUTES));

        assertThat(count).isZero();
    }

    // ── P4 — IMPOSSIBLE TRAVEL ────────────────────────────────────────────────
    @Test
    void findLastSuccessfulLogin_shouldReturnMostRecent() {
        // save two successful logins
        repository.save(buildEntry("U123", "IN", true));
        repository.save(buildEntry("U123", "SG", true));

        Optional<UserLoginHistory> lastLogin = repository
                .findTop1ByUserIdAndWasSuccessfulTrueOrderByLoggedInAtDesc("U123");

        assertThat(lastLogin).isPresent();
        // most recent is SG — saved last
        assertThat(lastLogin.get().getCountry()).isEqualTo("SG");
    }

    @Test
    void findLastSuccessfulLogin_shouldReturnEmpty_whenNoSuccessfulLogins() {
        repository.save(buildEntry("U123", "SG", false));

        Optional<UserLoginHistory> lastLogin = repository
                .findTop1ByUserIdAndWasSuccessfulTrueOrderByLoggedInAtDesc("U123");

        assertThat(lastLogin).isEmpty();
    }

    // ── P7 — NEW COUNTRY ──────────────────────────────────────────────────────
    @Test
    void existsByCountry_shouldReturnTrue_whenCountryKnown() {
        repository.save(buildEntry("U123", "SG", true));

        boolean exists = repository
                .existsByUserIdAndWasSuccessfulTrueAndCountry("U123", "SG");

        assertThat(exists).isTrue();
    }

    @Test
    void existsByCountry_shouldReturnFalse_whenCountryNew() {
        repository.save(buildEntry("U123", "SG", true));

        boolean exists = repository
                .existsByUserIdAndWasSuccessfulTrueAndCountry("U123", "US");

        assertThat(exists).isFalse();
    }

    @Test
    void existsByCountry_shouldReturnFalse_whenOnlyFailedLoginFromCountry() {
        // failed login from US — should not count as known country
        repository.save(buildEntry("U123", "US", false));

        boolean exists = repository
                .existsByUserIdAndWasSuccessfulTrueAndCountry("U123", "US");

        assertThat(exists).isFalse();
    }

    // ── P8 — LOGIN VELOCITY ───────────────────────────────────────────────────
    @Test
    void countLoginAttempts_shouldCountBothSuccessfulAndFailed() {
        Instant tenMinutesAgo = Instant.now().minus(10, ChronoUnit.MINUTES);

        repository.save(buildEntry("U123", "SG", true));
        repository.save(buildEntry("U123", "SG", false));
        repository.save(buildEntry("U123", "SG", false));

        int count = repository.countByUserIdAndLoggedInAtAfter(
                "U123", tenMinutesAgo);

        // P8 counts all attempts — successful and failed
        assertThat(count).isEqualTo(3);
    }

    @Test
    void countLoginAttempts_shouldReturnZero_whenNoAttempts() {
        int count = repository.countByUserIdAndLoggedInAtAfter(
                "U999", Instant.now().minus(10, ChronoUnit.MINUTES));

        assertThat(count).isZero();
    }
}