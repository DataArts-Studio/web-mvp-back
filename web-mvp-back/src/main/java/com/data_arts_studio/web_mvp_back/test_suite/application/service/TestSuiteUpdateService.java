package com.data_arts_studio.web_mvp_back.test_suite.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.data_arts_studio.web_mvp_back.test_suite.application.exception.TestSuiteBusinessException;
import com.data_arts_studio.web_mvp_back.test_suite.application.exception.TestSuiteErrorCode;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.UpdateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.UpdateTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.SaveTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.UpdateTestSuiteResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.validator.TestSuiteUpdateValidator;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TestSuiteUpdateService implements UpdateTestSuiteUseCase{
    private final LoadTestSuitePort loadTestSuitePort;
    private final SaveTestSuitePort saveTestSuitePort;
    private final TestSuiteUpdateValidator testSuiteUpdateValidator;


    @Override
    public UpdateTestSuiteResult updateTestSuite(UpdateTestSuiteCommand command) {

        testSuiteUpdateValidator.validate(command);
        TestSuiteId testSuiteId = new TestSuiteId(command.id());
        // TODO(authz): loadById만 쓰지 말고 command.projectId()와의 소속 일치 여부를 보장하는 조회/검증으로 확장할 것.
        TestSuite testSuite = loadTestSuitePort.loadById(testSuiteId).orElseThrow(() -> new TestSuiteBusinessException(TestSuiteErrorCode.TEST_SUITE_ID_NOT_FOUND));
        testSuite.rename(command.name());
        saveTestSuitePort.updateTestSuite(testSuite);

        return new UpdateTestSuiteResult(
            testSuite.getId().getId(),
            testSuite.getProjectId().getId(),
            testSuite.getName(),
            testSuite.getDescription()
        );
    }

    
    
}
