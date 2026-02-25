package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.mapper.TestSuiteMapper;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.CheckProjectExistsPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.CheckTestSuiteNamePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.SaveTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

import lombok.RequiredArgsConstructor;

// TestSuite 관련의 영속성 어댑터
@Component
@RequiredArgsConstructor
public class TestSuitePersistenceAdapter implements SaveTestSuitePort, 
                                                    CheckTestSuiteNamePort, 
                                                    CheckProjectExistsPort,
                                                    LoadTestSuitePort {

    private final TestSuiteJpaRepository testSuiteJpaRepository;
    private final TestSuiteMapper testSuiteMapper;
    private final ProjectJpaRepository projectJpaRepository;

    // 테스트 스위트 생성
    @Override
    public void createTestSuite(TestSuite testSuite) {
        TestSuiteJpaEntity entity = testSuiteMapper.toJpaEntity(testSuite);
        testSuiteJpaRepository.save(entity);
    }
    // 테스트 스위트 수정 
    @Override
    public void updateTestSuite(TestSuite testSuite) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTestSuite'");
    }


    // 테스트 스위트 이름 중복 검사
    @Override
    public boolean isTestSuiteNameDuplicated(String projectId, String name) {
        return testSuiteJpaRepository.existsByProjectIdAndNameAndArchivedAtIsNull(projectId, name);
    }
    // 프로젝트 존재 여부 검사
    @Override
    public boolean existsById(String projectId) {
       return projectJpaRepository.existsByArchivedAtIsNullAndId(projectId);
    }
    @Override
    public Optional<TestSuite> loadById(TestSuiteId testSuiteId) {
        return testSuiteJpaRepository.findById(testSuiteId.getId())
                .map(testSuiteMapper::toDomain);
    }


}
