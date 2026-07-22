package com.decision.decision_service.service;
import com.decision.decision_service.exception.PolicyConfigNotFound;
import com.decision.decision_service.model.entity.PolicyConfig;
import com.decision.decision_service.repository.PolicyConfigRepository;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyConfigService {

    @Autowired
    PolicyConfigRepository policyConfigRepository;

    @Cacheable("policyConfig")
    public PolicyConfig loadCurrentConfig(){
        return policyConfigRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new PolicyConfigNotFound("no policy found in cache"));
    }

    @CacheEvict(value = "policyConfig", allEntries = true)
    public void cacheEvict(){
        //no body required
        //spring handles it
    }
}
