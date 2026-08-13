package com.decision.decision_service.service;

import com.decision.decision_service.exception.ResourceNotFoundException;
import com.decision.decision_service.model.dto.DeviceListResponse;
import com.decision.decision_service.model.dto.DeviceResponse;
import com.decision.decision_service.model.entity.UserDeviceRegistry;
import com.decision.decision_service.repository.UserDeviceRegistryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserDeviceRegistryRepository userDeviceRegistryRepository;

    public DeviceListResponse getUserDevices(String userId){
        List<UserDeviceRegistry> listOfDevices = userDeviceRegistryRepository.findByUserIdAndDeletedAtIsNullOrderByLastSeenDesc(userId);
        List<DeviceResponse> devicesResponse = listOfDevices.stream()
                .map(this::toDevicesResponse)
                .toList();

        return DeviceListResponse.builder()
                .userId(userId)
                .devices(devicesResponse)
                .build();
    }

    private DeviceResponse toDevicesResponse(UserDeviceRegistry list){
        return (DeviceResponse.builder()
                        .deviceId(list.getDeviceId())
                        .firstSeen(list.getFirstSeen())
                        .lastSeen(list.getLastSeen())
                        .loginCount(list.getLoginCount())
                        .isTrusted(list.getIsTrusted())
                        .trustedAt(list.getTrustedAt())
                        .trustRevokedAt(list.getTrustRevokedAt())
                        .deletedAt(list.getDeletedAt())
                        .build());
    }

    @Transactional
    public DeviceResponse trustDeviceCheck(String userId, String deviceId){
        Optional<UserDeviceRegistry> existingDevice = userDeviceRegistryRepository.findByUserIdAndDeviceIdAndDeletedAtIsNull(userId, deviceId);
        if (existingDevice.isEmpty()){
            throw new ResourceNotFoundException("No device found for the given userId and deviceId to trust");
        }
        UserDeviceRegistry device = existingDevice.get();
        device.setIsTrusted(true);
        device.setTrustedAt(Instant.now());
        userDeviceRegistryRepository.save(device);
        return toDevicesResponse(device);
    }

    @Transactional
    public DeviceResponse revokeDeviceTrust(String userId, String deviceId){
        Optional<UserDeviceRegistry> existingDevice = userDeviceRegistryRepository.findByUserIdAndDeviceIdAndDeletedAtIsNull(userId, deviceId);
        if (existingDevice.isEmpty()){
            throw new ResourceNotFoundException("No device found for the given userId and deviceId to un-trust");
        }
        UserDeviceRegistry device = existingDevice.get();
        device.setIsTrusted(false);
        device.setTrustRevokedAt(Instant.now());
        userDeviceRegistryRepository.save(device);
        return toDevicesResponse(device);
    }
}
