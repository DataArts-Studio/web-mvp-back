package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id;

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
public class MilestoneTestSuiteJpaId implements Serializable {
    @Column(name = "milestone_id", nullable = false)
    private UUID milestoneId;

    @Column(name = "test_suite_id", nullable = false)
    private UUID testSuiteId;
}
