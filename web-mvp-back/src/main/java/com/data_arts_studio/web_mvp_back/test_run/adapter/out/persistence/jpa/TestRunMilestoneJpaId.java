package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TestRunMilestoneJpaId implements Serializable {
    @Column(name = "test_run_id", nullable = false)
    private UUID testRunId;

    @Column(name = "milestone_id", nullable = false)
    private UUID milestoneId;
}
