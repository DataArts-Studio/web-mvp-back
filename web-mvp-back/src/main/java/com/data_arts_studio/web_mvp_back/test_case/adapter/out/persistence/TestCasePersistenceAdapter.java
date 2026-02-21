package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaRepository;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.mapper.TestCaseMapper;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.GenerateCaseKeyPort;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.LoadTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.SaveTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;
import lombok.RequiredArgsConstructor;

/**
 * TestCase 서비스가 사용하는 포트 구현체
 */
@Component
@RequiredArgsConstructor
public class TestCasePersistenceAdapter implements SaveTestCasePort, LoadTestCasePort, GenerateCaseKeyPort {
    private final TestCaseJpaRepository testCaseJpaRepository;
    private final TestCaseMapper testCaseMapper;
    private static final Pattern CASE_KEY_PATTERN = Pattern.compile("^TC-(\\d+)$");


    /**
     * 해당 프로젝트의 기존 caseKey 중 최대 시퀀스를 찾아서 +1 한 새로운 caseKey 생성
     * @param projectId
     * @return 새로운 caseKey
     */
    @Override
    public String generateUniqueCaseKey(String projectId) {
        int maxSequence = testCaseJpaRepository.findAllByProjectId(projectId).stream()
                .map(TestCaseJpaEntity::getCaseKey)
                .mapToInt(this::extractSequence)
                .max()
                .orElse(0);
        int nextSequence = maxSequence + 1;
        return String.format("TC-%03d", nextSequence);
    }
    /**
     * 테스트 케이스 저장
      * caseKey가 없는 경우, generateUniqueCaseKey를 통해 caseKey 생성 후 저장
      * @param testCase 저장할 테스트 케이스 도메인 객체
     */
    @Override
    public void createTestCase(TestCase testCase) {
        TestCaseJpaEntity entity = testCaseMapper.toJpaEntity(testCase);
        testCaseJpaRepository.save(entity);
    }
    /**
     * 테스트 케이스 업데이트
      * caseKey가 없는 경우, generateUniqueCaseKey를 통해 caseKey 생성 후 저장
      * @param testCase 저장할 테스트 케이스 도메인 객체
     */
    @Override
    public void updateTestCase(TestCase testCase) {
        TestCaseJpaEntity entity = testCaseMapper.toJpaEntity(testCase);
        testCaseJpaRepository.save(entity);
    }
    /**
     * 식별자를 통해 테스트 케이스 도메인 엔티티를 로드
     * @param testCaseId 도메인 값 객체 식별자
     * @return 검색된 테스트 케이스 (없을 경우 Optional.empty())
     */
    @Override
    public Optional<TestCase> loadTestCase(TestCaseId testCaseId) {
       return testCaseJpaRepository.findById(testCaseId.getId())
                .map(testCaseMapper::toDomainEntity);
    }
    /**
     * 프로젝트에 속한 테스트 케이스 목록 로드
     * @param projectId 프로젝트 식별자
     * @return 테스트 케이스 목록
     */
    @Override
    public List<TestCase> loadByProjectId(String projectId) {
        return testCaseJpaRepository.findAllByProjectIdOrderBySortOrderAscCreatedAtAsc(projectId).stream()
                .map(testCaseMapper::toDomainEntity)
                .toList();
    }

    /**
     * caseKey에서 시퀀스 번호 추출
     * @param caseKey
     * @return
     */
    private int extractSequence(String caseKey) {
        if (caseKey == null) {
            return 0;
        }
        Matcher matcher = CASE_KEY_PATTERN.matcher(caseKey);
        if (!matcher.matches()) {
            return 0;
        }
        return Integer.parseInt(matcher.group(1));
    }

}
