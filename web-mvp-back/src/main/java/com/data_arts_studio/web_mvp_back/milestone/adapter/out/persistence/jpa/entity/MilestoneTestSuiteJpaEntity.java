package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id.MilestoneTestSuiteJpaId;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.entity.TestSuiteJpaEntity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "milestone_test_suites")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MilestoneTestSuiteJpaEntity {
    @EmbeddedId
    private MilestoneTestSuiteJpaId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("milestoneId")
    @JoinColumn(name = "milestone_id", nullable = false)
    private MilestoneJpaEntity milestone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("testSuiteId")
    @JoinColumn(name = "test_suite_id", nullable = false)
    private TestSuiteJpaEntity testSuite;

    public MilestoneTestSuiteJpaEntity(MilestoneJpaEntity milestone, TestSuiteJpaEntity testSuite) {
        this.id = new MilestoneTestSuiteJpaId(milestone.getId(), testSuite.getId());
        this.milestone = milestone;
        this.testSuite = testSuite;
    }
}
