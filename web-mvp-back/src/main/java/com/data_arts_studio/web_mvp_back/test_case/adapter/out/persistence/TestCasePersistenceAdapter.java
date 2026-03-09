package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaRepository;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.mapper.TestCaseMapper;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.ArchiveTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.GenerateCaseKeyPort;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.SaveTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import lombok.RequiredArgsConstructor;

/**
 * TestCase 서비스가 사용하는 포트 구현체
 */
@Component
@RequiredArgsConstructor
public class TestCasePersistenceAdapter implements SaveTestCasePort, GenerateCaseKeyPort, ArchiveTestCasePort {
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
        int maxSequence = testCaseJpaRepository.findAllByProjectId(UUID.fromString(projectId)).stream()
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

    /**
     * 테스트 케이스 아카이브
     * 
     * @param 아카이될 테스트 케이스 도메인 객체
     */
    @Override
    public void archive(TestCase testCase) {
        testCaseJpaRepository.save(testCaseMapper.toJpaEntity(testCase));
    }

}
