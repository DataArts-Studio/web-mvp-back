package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaRepository;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.mapper.TestCaseMapper;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.LoadTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestCaseReadAdapter implements LoadTestCasePort {

    private final TestCaseJpaRepository testCaseJpaRepository;
    private final TestCaseMapper testCaseMapper;
    
    
    /**
     * 식별자를 통해 테스트 케이스 도메인 엔티티를 로드
     * @param testCaseId 도메인 값 객체 식별자
     * @return 검색된 테스트 케이스 (없을 경우 Optional.empty())
     */

    @Override
    public Optional<TestCase> loadTestCase(TestCaseId testCaseId) {
        return testCaseJpaRepository.findById(UUID.fromString(testCaseId.getId()))
                .map(testCaseMapper::toDomainEntity);
    }
    /**
     * 프로젝트에 속한 테스트 케이스 목록 로드
     * @param projectId 프로젝트 식별자
     * @return 테스트 케이스 목록
     */
    @Override
    public List<TestCase> loadByProjectId(String projectId) {
        return testCaseJpaRepository.findAllByProjectIdOrderBySortOrderAscCreatedAtAsc(UUID.fromString(projectId)).stream()
                .map(testCaseMapper::toDomainEntity)
                .toList();
    }
}
