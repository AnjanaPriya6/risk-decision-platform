package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.IpReputationCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface IpReputationCacheRepository extends JpaRepository<IpReputationCache, String> {
    Optional<IpReputationCache> findByIpAddressAndExpiresAtAfter(String ipAddress, Instant since);
}
