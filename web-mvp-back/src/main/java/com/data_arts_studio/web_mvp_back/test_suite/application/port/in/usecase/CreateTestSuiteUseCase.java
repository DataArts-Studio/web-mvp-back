package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.CreateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.CreateTestSuiteResult;

public interface CreateTestSuiteUseCase {
    CreateTestSuiteResult createTestSuite(CreateTestSuiteCommand command);
}
