package com.decision.decision_service.repository;

import com.decision.decision_service.model.entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, UUID> {
    //first login - P0
    boolean existsByUserIdAndWasSuccessfulTrue(String userId);

    //Brute force - P1
    int countByUserIdAndWasSuccessfulFalseAndLoggedInAtAfter(String userId, Instant since);

    //ImpossibleTravel - P4
    Optional<UserLoginHistory> findTop1ByUserIdAndWasSuccessfulTrueOrderByLoggedInAtDesc(String userId);

    //New Country - P7
    boolean existsByUserIdAndWasSuccessfulTrueAndCountry(String userId, String country);

    //Login Velocity - P8
    int countByUserIdAndLoggedInAtAfter(String userId, Instant since);

}
