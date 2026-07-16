package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.PolicyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PolicyConfigRepository extends JpaRepository<PolicyConfig, UUID> {
    Optional<PolicyConfig> findTopByOrderByCreatedAtDesc();
}

//implementing an interface instead of a class and springboot will automatically write the query