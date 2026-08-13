package com.decision.decision_service.controller;

import com.decision.decision_service.model.dto.DeviceListResponse;
import com.decision.decision_service.model.dto.DeviceResponse;
import com.decision.decision_service.model.dto.SoftDeleteResponseDto;
import com.decision.decision_service.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserProfileControllerV1 {

    private final UserProfileService userProfileService;

    @GetMapping("{userId}/devices")
    public ResponseEntity<DeviceListResponse> getDevices(
            @PathVariable String userId){
        DeviceListResponse response = userProfileService.getUserDevices(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{userId}/devices/{deviceId}/trust")
    public ResponseEntity<DeviceResponse> trustDevice(
            @PathVariable(name="userId") String userId,
            @PathVariable(name="deviceId") String deviceId
    ){
        DeviceResponse response = userProfileService.trustDeviceCheck(userId,deviceId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{userId}/devices/{deviceId}/trust")
    public ResponseEntity<DeviceResponse> unTrustDevice(
            @PathVariable(name="userId") String userId,
            @PathVariable(name="deviceId") String deviceId
    ){
        DeviceResponse response = userProfileService.revokeDeviceTrust(userId,deviceId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{userId}/devices/{deviceId}")
    public ResponseEntity<SoftDeleteResponseDto> softDeleteDevice(
            @PathVariable(name="userId") String userId,
            @PathVariable(name="deviceId") String deviceId
    ){
        SoftDeleteResponseDto response = userProfileService.softDelete(userId,deviceId);
        return ResponseEntity.ok(response);
    }
}
