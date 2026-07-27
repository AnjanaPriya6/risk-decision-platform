package com.decision.decision_service.controller;

import com.decision.decision_service.model.dto.DeviceListResponse;
import com.decision.decision_service.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/")
@AllArgsConstructor
public class UserProfileControllerV1 {

    public final UserProfileService userProfileService;

    @GetMapping("{userId}/devices")
    public ResponseEntity<DeviceListResponse> getDevices(
            @PathVariable String userId){
        DeviceListResponse response = userProfileService.getUserDevices(userId);
        return ResponseEntity.ok(response);
    }

}
