package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "milestone_test_cases")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MilestoneTestCaseJpaEntity {
    @EmbeddedId
    private MilestoneTestCaseJpaId id;
}
