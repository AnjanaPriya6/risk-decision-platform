package com.decision.decision_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CacheWarmupService {

    private final PolicyConfigService policyConfigService;

    @EventListener(ApplicationReadyEvent.class)
    public void warmUpCache() {
        policyConfigService.loadCurrentConfig();
    }
}