package com.decision.decision_service.service;

import com.decision.decision_service.model.dto.DeviceListResponse;
import com.decision.decision_service.model.dto.DeviceResponse;
import com.decision.decision_service.model.entity.UserDeviceRegistry;
import com.decision.decision_service.repository.UserDeviceRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    UserDeviceRegistryRepository userDeviceRegistryRepository;

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

    public DeviceResponse toDevicesResponse(UserDeviceRegistry list){
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

}
