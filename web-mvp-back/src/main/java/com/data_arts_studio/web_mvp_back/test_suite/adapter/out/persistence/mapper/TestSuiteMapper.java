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
        return new TestSuiteJpaEntity(
            testSuite.getId().getId(),
            testSuite.getProjectId().getId(), 
            testSuite.getName(), 
            testSuite.getDescription(),
            testSuite.getSortOrder(), 
            testSuite.getLifecycleStatus(),
            testSuite.getCreatedAt(), 
            testSuite.getUpdatedAt(), 
            testSuite.getArchivedAt());

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
