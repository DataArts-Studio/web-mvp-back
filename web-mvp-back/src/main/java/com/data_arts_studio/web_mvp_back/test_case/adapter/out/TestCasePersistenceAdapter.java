package com.data_arts_studio.web_mvp_back.test_case.adapter.out;

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

    @Override
    public void save(TestCase testCase) {
        TestCaseJpaEntity entity = testCaseMapper.toJpaEntity(testCase);
        testCaseJpaRepository.save(entity);
    }

    @Override
    public void updateTestCase(TestCase testCase) {
    
        throw new UnsupportedOperationException("Unimplemented method 'updateTestCase'");
    }

    @Override
    public Optional<TestCase> loadTestCase(TestCaseId testCaseId) {
       return testCaseJpaRepository.findById(testCaseId.getId())
                .map(testCaseMapper::toDomainEntity);
    }

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
