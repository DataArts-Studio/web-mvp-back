package com.data_arts_studio.web_mvp_back.test_case.application.port.in;

import com.data_arts_studio.web_mvp_back.test_case.application.service.CreateTestCaseResult;

public interface CreateTestCaseUseCase {
    CreateTestCaseResult createTestCase(CreateTestCaseCommand command);
}
