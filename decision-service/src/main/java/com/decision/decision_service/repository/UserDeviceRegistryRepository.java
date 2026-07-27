package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.UserDeviceRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDeviceRegistryRepository extends JpaRepository<UserDeviceRegistry, UUID> {
    Boolean existsByUserIdAndDeviceIdAndDeletedAtIsNull(String userId, String deviceId);

    Optional<UserDeviceRegistry> findByUserIdAndDeviceIdAndDeletedAtIsNull(String userId, String deviceId);

    List<UserDeviceRegistry> findByUserIdAndDeletedAtIsNullOrderByLastSeenDesc(String userId);
}

