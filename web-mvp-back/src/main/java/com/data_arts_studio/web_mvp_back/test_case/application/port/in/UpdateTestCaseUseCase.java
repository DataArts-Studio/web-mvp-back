package com.data_arts_studio.web_mvp_back.test_case.application.port.in;

import com.data_arts_studio.web_mvp_back.test_case.application.service.UpdateTestCaseResult;

public interface UpdateTestCaseUseCase {
    UpdateTestCaseResult updateTestCase(UpdateTestCaseCommand command);
    
}
