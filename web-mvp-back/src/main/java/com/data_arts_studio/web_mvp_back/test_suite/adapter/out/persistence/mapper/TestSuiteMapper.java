package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;


@Component
public class TestSuiteMapper {
    // Domain Entity -> JPA Entity
    public TestSuiteJpaEntity toJpaEntity(TestSuite testSuite) {
        return TestSuiteJpaEntity.builder()
                .id(testSuite.getId().getId())
                .projectId(testSuite.getProjectId().getId())
                .name(testSuite.getName())
                .description(testSuite.getDescription())
                .sortOrder(testSuite.getSortOrder())
                .lifecycleStatus(testSuite.getLifecycleStatus())
                .createdAt(testSuite.getCreatedAt())
                .updatedAt(testSuite.getUpdatedAt())
                .archivedAt(testSuite.getArchivedAt())
                .build();

    }
    // JPA Entity -> Domain Entity
    public TestSuite toDomain(TestSuiteJpaEntity entity) {
        return new TestSuite(
            new TestSuiteId(entity.getId()),
            new ProjectId(entity.getProjectId()),
            entity.getName(),
            entity.getDescription(),
            entity.getSortOrder(),
            entity.getLifecycleStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getArchivedAt()
        );
        
    }
}
