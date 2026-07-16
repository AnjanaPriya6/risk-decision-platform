package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.DecisionAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DecisionAuditRepository extends JpaRepository<DecisionAudit, UUID> {
    Optional<DecisionAudit> findByCorrelationId(String CorrelationId);
    Page<DecisionAudit> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
}
