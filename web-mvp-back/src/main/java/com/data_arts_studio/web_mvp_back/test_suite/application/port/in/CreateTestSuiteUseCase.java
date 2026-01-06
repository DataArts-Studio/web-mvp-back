package com.data_arts_studio.web_mvp_back.test_suite.application.port.in;

import com.data_arts_studio.web_mvp_back.test_suite.application.service.TestSuiteResult;

public interface CreateTestSuiteUseCase {
    TestSuiteResult createTestSuite(CreateTestSuiteCommand command);
}
