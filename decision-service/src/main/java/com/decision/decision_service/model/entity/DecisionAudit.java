package com.decision.decision_service.model.entity;

import com.decision.decision_service.model.enums.ChannelType;
import com.decision.decision_service.model.enums.DecisionOutcome;
import com.decision.decision_service.model.enums.EventType;
import com.decision.decision_service.model.enums.StepUpResult;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    //not sure on how to write the arrays :(

    @Column(name = "risk_score", updatable = false, nullable = false)
    private double risk_score;

    @Column(name = "policy_version", nullable = false, updatable = false)
    private String policyVersion;

    @Column(name = "model_version", nullable = false, updatable = false)
    private String model_version;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", updatable = false, columnDefinition = "channel_enum")
    private ChannelType channel;

    @Column(name = "decision_time_ms", updatable = false)
    private double decisionTimeMs;

    @Column(name = "ip_address", updatable = false)
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_up_outcome", updatable = false, columnDefinition = "step_up_outcome_enum")
    private StepUpResult stepUpOutcome;

    //don't know about the rest two columns with timestamps

}
