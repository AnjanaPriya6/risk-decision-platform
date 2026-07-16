package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.UserDeviceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class UserDeviceRegistryRepositoryTest {

    @Autowired
    private UserDeviceRegistryRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    private UserDeviceRegistry buildDevice(String userId,
                                           String deviceId,
                                           boolean isTrusted) {
        return UserDeviceRegistry.builder()
                .userId(userId)
                .deviceId(deviceId)
                .firstSeen(Instant.now())
                .lastSeen(Instant.now())
                .loginCount(1)
                .isTrusted(isTrusted)
                .build();
    }

    // ── existsByUserIdAndDeviceIdAndDeletedAtIsNull ───────────────────────────

    @Test
    void exists_shouldReturnFalse_whenDeviceNotRegistered() {
        boolean exists = repository
                .existsByUserIdAndDeviceIdAndDeletedAtIsNull("U123", "device_new");

        assertThat(exists).isFalse();
    }

    @Test
    void exists_shouldReturnTrue_whenDeviceRegistered() {
        repository.save(buildDevice("U123", "device_abc", false));

        boolean exists = repository
                .existsByUserIdAndDeviceIdAndDeletedAtIsNull("U123", "device_abc");

        assertThat(exists).isTrue();
    }

    @Test
    void exists_shouldReturnFalse_whenDeviceSoftDeleted() {
        UserDeviceRegistry device = buildDevice("U123", "device_abc", false);
        device.setDeletedAt(Instant.now());
        repository.save(device);

        boolean exists = repository
                .existsByUserIdAndDeviceIdAndDeletedAtIsNull("U123", "device_abc");

        // soft deleted device should not be found
        assertThat(exists).isFalse();
    }

    @Test
    void exists_shouldReturnFalse_whenDifferentUser() {
        repository.save(buildDevice("U123", "device_abc", false));

        boolean exists = repository
                .existsByUserIdAndDeviceIdAndDeletedAtIsNull("U999", "device_abc");

        assertThat(exists).isFalse();
    }

    // ── findByUserIdAndDeviceIdAndDeletedAtIsNull ─────────────────────────────

    @Test
    void findByUserIdAndDeviceId_shouldReturnDevice_whenExists() {
        repository.save(buildDevice("U123", "device_abc", false));

        Optional<UserDeviceRegistry> result = repository
                .findByUserIdAndDeviceIdAndDeletedAtIsNull("U123", "device_abc");

        assertThat(result).isPresent();
        assertThat(result.get().getUserId()).isEqualTo("U123");
        assertThat(result.get().getDeviceId()).isEqualTo("device_abc");
    }

    @Test
    void findByUserIdAndDeviceId_shouldReturnEmpty_whenNotExists() {
        Optional<UserDeviceRegistry> result = repository
                .findByUserIdAndDeviceIdAndDeletedAtIsNull("U123", "device_abc");

        assertThat(result).isEmpty();
    }

    @Test
    void findByUserIdAndDeviceId_shouldReturnEmpty_whenSoftDeleted() {
        UserDeviceRegistry device = buildDevice("U123", "device_abc", false);
        device.setDeletedAt(Instant.now());
        repository.save(device);

        Optional<UserDeviceRegistry> result = repository
                .findByUserIdAndDeviceIdAndDeletedAtIsNull("U123", "device_abc");

        assertThat(result).isEmpty();
    }

    // ── findByUserIdAndDeletedAtIsNullOrderByLastSeenDesc ────────────────────

    @Test
    void findAllDevices_shouldReturnAllActiveDevices() {
        repository.save(buildDevice("U123", "device_1", false));
        repository.save(buildDevice("U123", "device_2", true));

        // soft deleted — should not appear
        UserDeviceRegistry deleted = buildDevice("U123", "device_3", false);
        deleted.setDeletedAt(Instant.now());
        repository.save(deleted);

        List<UserDeviceRegistry> devices = repository
                .findByUserIdAndDeletedAtIsNullOrderByLastSeenDesc("U123");

        assertThat(devices).hasSize(2);
        assertThat(devices).extracting(UserDeviceRegistry::getDeviceId)
                .containsExactlyInAnyOrder("device_1", "device_2");
    }

    @Test
    void findAllDevices_shouldReturnEmpty_whenNoDevices() {
        List<UserDeviceRegistry> devices = repository
                .findByUserIdAndDeletedAtIsNullOrderByLastSeenDesc("U999");

        assertThat(devices).isEmpty();
    }

    @Test
    void findAllDevices_shouldNotReturnOtherUsersDevices() {
        repository.save(buildDevice("U123", "device_abc", false));
        repository.save(buildDevice("U456", "device_xyz", false));

        List<UserDeviceRegistry> devices = repository
                .findByUserIdAndDeletedAtIsNullOrderByLastSeenDesc("U123");

        assertThat(devices).hasSize(1);
        assertThat(devices.get(0).getUserId()).isEqualTo("U123");
    }

    // ── trust lifecycle ───────────────────────────────────────────────────────

    @Test
    void save_shouldPersistTrustStatus() {
        UserDeviceRegistry device = buildDevice("U123", "device_abc", true);
        device.setTrustedAt(Instant.now());
        repository.save(device);

        Optional<UserDeviceRegistry> found = repository
                .findByUserIdAndDeviceIdAndDeletedAtIsNull("U123", "device_abc");

        assertThat(found).isPresent();
        assertThat(found.get().getIsTrusted()).isTrue();
        assertThat(found.get().getTrustedAt()).isNotNull();
    }
}