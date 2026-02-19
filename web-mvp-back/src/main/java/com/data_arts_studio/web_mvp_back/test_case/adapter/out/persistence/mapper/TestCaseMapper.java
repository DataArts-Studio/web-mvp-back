package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.mapper;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

@Component
public class TestCaseMapper {
    // Domain -> JPA Entity
    public TestCaseJpaEntity toJpaEntity(TestCase testCase) {
        return TestCaseJpaEntity.builder()
                .id(testCase.getId().getId())
                .projectId(testCase.getProjectId().getId())
                // testSuiteId가 null일 수 있으므로 조건부 처리
                .testSuiteId(testCase.getTestSuiteId() != null ? testCase.getTestSuiteId().getId() : null)
                .caseKey(testCase.getCaseKey())
                .name(testCase.getName())
                .priority(testCase.getPriority()) 
                .testType(testCase.getTestType())
                .tags(testCase.getTags() != null ? testCase.getTags().toArray(new String[0]) : null)
                .preCondition(testCase.getPreCondition())
                .steps(testCase.getSteps())
                .expectedResult(testCase.getExpectedResult())
                .sortOrder(testCase.getSortOrder())
                .resultStatus(testCase.getResultStatus())
                .lifecycleStatus(testCase.getLifecycleStatus())
                .createdAt(testCase.getCreatedAt())
                .updatedAt(testCase.getUpdatedAt())
                .archivedAt(testCase.getArchivedAt())
                .build();
    }

    // JPA Entity -> Domain
    public TestCase toDomainEntity(TestCaseJpaEntity entity) {
        return new TestCase(
                new TestCaseId(entity.getId()),
                new ProjectId(entity.getProjectId()),
                // testSuiteId가 null일 수 있으므로 조건부 처리
                entity.getTestSuiteId() != null ? new TestSuiteId(entity.getTestSuiteId()) : null,
                entity.getCaseKey(),
                entity.getName(),
                entity.getPriority(),
                entity.getTestType(),
                entity.getTags() != null ? Arrays.asList(entity.getTags()) : List.of(),
                entity.getPreCondition(),
                entity.getSteps(),
                entity.getExpectedResult(),
                entity.getSortOrder(),
                entity.getResultStatus(),
                // BaseEntity 감사 필드 복원
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getArchivedAt(),
                entity.getLifecycleStatus()
        );
    }
}
