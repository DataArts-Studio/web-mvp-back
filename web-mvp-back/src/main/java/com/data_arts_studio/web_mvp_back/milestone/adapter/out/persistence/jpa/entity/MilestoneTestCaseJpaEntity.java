package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id.MilestoneTestCaseJpaId;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaEntity;

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
@Table(name = "milestone_test_cases")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MilestoneTestCaseJpaEntity {
    @EmbeddedId
    private MilestoneTestCaseJpaId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("milestoneId")
    @JoinColumn(name = "milestone_id", nullable = false)
    private MilestoneJpaEntity milestone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("testCaseId")
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCaseJpaEntity testCase;

    public MilestoneTestCaseJpaEntity(MilestoneJpaEntity milestone, TestCaseJpaEntity testCase) {
        this.id = new MilestoneTestCaseJpaId(milestone.getId(), testCase.getId());
        this.milestone = milestone;
        this.testCase = testCase;
    }
}
