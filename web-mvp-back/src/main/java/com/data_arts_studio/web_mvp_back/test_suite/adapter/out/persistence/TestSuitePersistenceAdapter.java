package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.mapper.TestSuiteMapper;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.CheckTestSuiteNamePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.SaveTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;

import lombok.RequiredArgsConstructor;

// TestSuite 관련의 영속성 어댑터
@Component
@RequiredArgsConstructor
public class TestSuitePersistenceAdapter implements SaveTestSuitePort, CheckTestSuiteNamePort{
    private final TestSuiteJpaRepository testSuiteJpaRepository;
    private final TestSuiteMapper testSuiteMapper;

    @Override
    public void save(TestSuite testSuite) {
        TestSuiteJpaEntity entity = testSuiteMapper.toJpaEntity(testSuite);
        testSuiteJpaRepository.save(entity);
    }

    @Override
    public boolean isTestSuiteNameDuplicated(String projectId, String name) {
        return testSuiteJpaRepository.existsByProjectIdAndNameAndDeletedAtIsNull(projectId, name);
    }


}
