package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.mapper.TestSuiteMapper;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.CheckProjectExistsPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.CheckTestSuiteNamePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.SaveTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;

import lombok.RequiredArgsConstructor;

// TestSuite 관련의 영속성 어댑터
@Component
@RequiredArgsConstructor
public class TestSuitePersistenceAdapter implements SaveTestSuitePort, CheckTestSuiteNamePort, CheckProjectExistsPort{
    private final TestSuiteJpaRepository testSuiteJpaRepository;
    private final TestSuiteMapper testSuiteMapper;
    private final ProjectJpaRepository projectJpaRepository;

    // 테스트 스위트 저장
    @Override
    public void save(TestSuite testSuite) {
        TestSuiteJpaEntity entity = testSuiteMapper.toJpaEntity(testSuite);
        testSuiteJpaRepository.save(entity);
    }
    // 테스트 스위트 이름 중복 검사
    @Override
    public boolean isTestSuiteNameDuplicated(String projectId, String name) {
        return testSuiteJpaRepository.existsByProjectIdAndNameAndDeletedAtIsNull(projectId, name);
    }
    // 프로젝트 존재 여부 검사
    @Override
    public boolean existsById(String projectId) {
       return projectJpaRepository.existsByIdAndDeletedAtIsNull(projectId);
    }


}
