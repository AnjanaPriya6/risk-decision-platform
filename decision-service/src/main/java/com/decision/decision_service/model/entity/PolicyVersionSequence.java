package com.decision.decision_service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="policy_version_sequence")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PolicyVersionSequence {

    //natural unique key as date is always unique
    @Id
    @Column(name = "version_date", nullable = false, updatable = false)
    private LocalDate versionDate;

    //YYYY-MM-DD.N where N is lastSequence. Automaticlaly incremented by postgresql trigger
    @Column(name = "last_sequence", nullable = false)
    private Integer lastSequence;
}
