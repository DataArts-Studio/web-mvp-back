package com.data_arts_studio.web_mvp_back.test_case.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_case.application.port.in.command.CreateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.service.result.CreateTestCaseResult;

public interface CreateTestCaseUseCase {
    CreateTestCaseResult createTestCase(CreateTestCaseCommand command);
}
