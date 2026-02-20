package com.data_arts_studio.web_mvp_back.test_case.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.test_case.application.port.in.UpdateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.UpdateTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.LoadTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.SaveTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseBusinessException;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseErrorCode;
import com.data_arts_studio.web_mvp_back.test_case.application.validator.TestCaseValidator;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseUpdateService implements UpdateTestCaseUseCase{
    private final LoadTestCasePort loadTestCasePort;
    private final SaveTestCasePort saveTestCasePort;
    private final TestCaseValidator testCaseUpdateValidator;
    private final LoadTestSuitePort loadTestSuitePort;

    @Override
    public UpdateTestCaseResult updateTestCase(UpdateTestCaseCommand command) {

        testCaseUpdateValidator.validate(command);
        TestCaseId testCaseId = new TestCaseId(command.testCaseId());
        TestCase testCase = loadTestCasePort.loadTestCase(testCaseId)
                        .orElseThrow(() -> new TestCaseBusinessException(TestCaseErrorCode.TESTCASE_ID_NOT_FOUND));

        TestSuiteId testSuiteId = command.testSuiteId() == null ? null : new TestSuiteId(command.testSuiteId());
        testCase.updateDetails(testSuiteId,
                               command.name(), 
                               command.testType(),
                               command.tags(),
                               command.preCondition(), 
                               command.steps(),
                               command.expectedResult());
        saveTestCasePort.save(testCase);

        // 테스트 스위트 이름 불러오기 (있을 경우)
        String testSuiteName = null;
        if (testSuiteId != null) {
            testSuiteName = loadTestSuitePort.loadById(testSuiteId)
                .map(TestSuite::getName)
                .orElse(null);
        }
    return new UpdateTestCaseResult(
            testCase.getId().getId(),
            testCase.getProjectId().getId(),
            testCase.getTestSuiteId() != null ? testCase.getTestSuiteId().getId() : null,
            testSuiteName,
            testCase.getCaseKey(),
            testCase.getName(),
            testCase.getTestType(),
            testCase.getResultStatus(),
            testCase.getTags(),
            testCase.getPreCondition(),
            testCase.getSteps(),
            testCase.getExpectedResult(),
            testCase.getUpdatedAt()
    );  
    }
    
}
