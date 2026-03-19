package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.entity.TestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.repository.TestSuiteJpaRepository;
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

    // TODO(authz): 프로젝트 경계 검증을 단순화하려면 findByIdAndProjectId 성격의 로드 메서드도 포트로 노출하는 방향을 검토할 것.

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
        TestSuiteJpaEntity entity = testSuiteMapper.toJpaEntity(testSuite);
        testSuiteJpaRepository.save(entity);
    }
    // 테스트 스위트 이름 중복 검사
    @Override
    public boolean isTestSuiteNameDuplicated(String projectId, String name) {
        return testSuiteJpaRepository.existsByProjectIdAndNameAndArchivedAtIsNull(UUID.fromString(projectId), name);
    }
    // 
    @Override
    public boolean isTestSuiteNameDuplicatedExceptId(String projectId, String name, String excludeSuiteId) {
        return testSuiteJpaRepository.existsByProjectIdAndNameAndIdNotAndArchivedAtIsNull(
                UUID.fromString(projectId), name, UUID.fromString(excludeSuiteId));
    }
    // 프로젝트 존재 여부 검사
    @Override
    public boolean existsById(String projectId) {
       return projectJpaRepository.existsByArchivedAtIsNullAndId(UUID.fromString(projectId));
    }
    // 식별자를 통해 테스트 스위트 도메인을 로드
    @Override
    public Optional<TestSuite> loadById(TestSuiteId testSuiteId) {
        return testSuiteJpaRepository.findById(UUID.fromString(testSuiteId.getId()))
                .map(testSuiteMapper::toDomain);
    }


}
