package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.PolicyVersionSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PolicyVersionSequenceRepository extends JpaRepository<PolicyVersionSequence, LocalDate> {
}
