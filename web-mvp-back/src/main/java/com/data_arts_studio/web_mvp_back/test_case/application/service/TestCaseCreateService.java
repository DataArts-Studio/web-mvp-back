package com.data_arts_studio.web_mvp_back.test_case.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.GenerateCaseKeyPort;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.SaveTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.application.validator.TestCaseValidator;
import com.data_arts_studio.web_mvp_back.test_case.domain.ResultStatus;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseCreateService implements CreateTestCaseUseCase{
    private final SaveTestCasePort saveTestCasePort;
    private final GenerateCaseKeyPort generateCaseKeyPort;
    private final TestCaseValidator testCaseCreateValidator;

    private final LoadTestSuitePort loadTestSuitePort;

    
    /**
     * 테스트 케이스 생성
     * @param CreateTestCaseCommand 
     * @return TestCaseResult
     */

    @Override
    public TestCaseResult createTestCase(CreateTestCaseCommand command) {
        testCaseCreateValidator.validate(command);
        TestCaseId testCaseId = TestCaseId.create();
        String caseKey = generateCaseKeyPort.generateUniqueCaseKey();
        TestCase testCase = new TestCase(testCaseId, 
                            new ProjectId(command.projectId()), 
                            command.testSuiteId(), 
                                         caseKey, command.name(), 
                                         command.priority(), 
                                         command.testType(), 
                                         command.tags(), 
                                         command.preCondition(), 
                                         command.steps(), 
                                         command.expectedResult(), 
                                         0, 
                                         ResultStatus.UNTESTED);
        saveTestCasePort.save(testCase);

        // 테스트 스위트 이름 불러오기 (있을 경우)
        String testSuiteName = null;
        if (command.testSuiteId() != null && !command.testSuiteId().isBlank()) {
            testSuiteName = loadTestSuitePort.loadById(new TestSuiteId(command.testSuiteId()))
                .map(TestSuite::getName)
                .orElse(null);
        }

        return new TestCaseResult(
            testCaseId.getId(),
            command.projectId(),
            // testSuiteId가 null일 수 있으므로 조건부 처리
            testCase.getTestSuiteId() != null ? testCase.getTestSuiteId().getId() : null,
            testSuiteName, // 조회한 이름 사용 (없으면 null)
            caseKey,
            command.name(),
            command.priority(),
            command.testType(),
            ResultStatus.UNTESTED,
            command.tags(),
            command.preCondition(),
            command.steps(),
            command.expectedResult()
        );
    }
}
