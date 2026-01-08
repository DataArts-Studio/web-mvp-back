package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;


@Component
public class TestSuiteMapper {
    // Domain Entity -> JPA Entity
    public static TestSuiteJpaEntity toJpaEntity(TestSuite testSuite) {
        return new TestSuiteJpaEntity(
            testSuite.getId().getId().toString(), 
            testSuite.getProjectId().getId().toString(), 
            testSuite.getName(), 
            testSuite.getSortOrder(), 
            testSuite.getCreatedAt(), 
            testSuite.getUpdatedAt(), 
            testSuite.getDeletedAt());

    }
    // JPA Entity -> Domain Entity
    public static TestSuite toDomain(TestSuiteJpaEntity entity) {
        return new TestSuite(
            new TestSuiteId(entity.getId()),
            new ProjectId(entity.getProjectId()),
            entity.getName(),
            entity.getSortOrder(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
        
    }
}
