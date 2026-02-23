package com.data_arts_studio.web_mvp_back.test_case.application.service;

import java.util.List;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.TestCaseListItemResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.TestCaseDetailRespose;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.GetTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.LoadTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseBusinessException;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseErrorCode;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestCaseGetService implements GetTestCaseUseCase {

    private final LoadTestCasePort loadTestCasePort;
    private final LoadTestSuitePort loadTestSuitePort;

    // 특정 테스트 케이스 상세 조회
    @Override
    public TestCaseDetailRespose getTestCaseDetails(String projectId, String testCaseId) {
        TestCase testCase = loadTestCasePort.loadTestCase(new TestCaseId(testCaseId))
                .orElseThrow(() -> new TestCaseBusinessException(TestCaseErrorCode.TESTCASE_ID_NOT_FOUND));

        // 다른 프로젝트의 테스트 케이스 접근 방지
        if (!testCase.getProjectId().getId().equals(projectId)) {
            throw new TestCaseBusinessException(TestCaseErrorCode.TESTCASE_ID_NOT_FOUND);
        }

        String testSuiteName = null;
        if (testCase.getTestSuiteId() != null) {
            TestSuiteId testSuiteId = testCase.getTestSuiteId();
            testSuiteName = loadTestSuitePort.loadById(testSuiteId)
                    .map(TestSuite::getName)
                    .orElse(null);
        }

        return TestCaseDetailRespose.builder()
                .id(testCase.getId().getId())
                .projectId(testCase.getProjectId().getId())
                .caseKey(testCase.getCaseKey())
                .resultStatus(testCase.getResultStatus().name())
                .name(testCase.getName())
                .testSuiteId(testCase.getTestSuiteId() != null ? testCase.getTestSuiteId().getId() : null)
                .testSuiteName(testSuiteName)
                .testType(testCase.getTestType())
                .createdAt(testCase.getCreatedAt())
                .updatedAt(testCase.getUpdatedAt())
                .tags(testCase.getTags())
                .preCondition(testCase.getPreCondition())
                .steps(testCase.getSteps())
                .expectedResult(testCase.getExpectedResult())
                .build();
    }

    // 프로젝트 내 모든 테스트 케이스 조회
    @Override
    public List<TestCaseListItemResponse> getProjectTestCases(String projectId) {
        return loadTestCasePort.loadByProjectId(projectId).stream()
                .map((TestCase testCase) -> new TestCaseListItemResponse(
                        testCase.getId().getId(),
                        testCase.getProjectId().getId(),
                        testCase.getName(),
                        testCase.getUpdatedAt() != null ? testCase.getUpdatedAt() : testCase.getCreatedAt()))
                .toList();
    }
}
