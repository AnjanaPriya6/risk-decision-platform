package com.decision.decision_service.model.entity;

import com.decision.decision_service.model.enums.ChannelType;
import com.decision.decision_service.model.enums.DecisionOutcome;
import com.decision.decision_service.model.enums.EventType;
import com.decision.decision_service.model.enums.StepUpResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "decision_audit")
//everythign in this table is set to updatable = false because once the decision is made,
// we don't want anyone to change it
public class DecisionAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "correlation_id", nullable = false, unique = true, updatable = false)
    private String correlationId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, updatable = false, columnDefinition = "event_type_enum")
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision", nullable = false, updatable = false, columnDefinition = "decision_outcome_enum")
    private DecisionOutcome decision;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "reason_codes", nullable = false, updatable = false, columnDefinition = "text[]")
    private String[] reasonCodes;

    @Column(name = "risk_score", updatable = false, nullable = false)
    private BigDecimal riskScore;

    @Column(name = "policy_version", nullable = false, updatable = false)
    private String policyVersion;

    @Column(name = "model_version", nullable = false, updatable = false)
    private String modelVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", updatable = false, columnDefinition = "channel_enum")
    private ChannelType channel;

    @Column(name = "decision_time_ms", updatable = false)
    private Integer decisionTimeMs;

    @Column(name = "ip_address", updatable = false)
    private String ipAddress;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "step_up_outcome", columnDefinition = "step_up_outcome_enum")
    private StepUpResult stepUpOutcome;

    @Setter
    @Column(name = "step_up_completed_at")
    private Instant stepUpCompletedAt;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

}

//removed setter for the entire class because only stepUpOutcome and stepUpCompletedAt can be updated after creation
