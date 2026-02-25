package com.data_arts_studio.web_mvp_back.test_suite.application.port.in;

import com.data_arts_studio.web_mvp_back.test_suite.application.service.UpdateTestSuiteResult;

// 테스트 스위트 수정 유즈 케이스
public interface UpdateTestSuiteUseCase {
    UpdateTestSuiteResult updateTestSuite(UpdateTestSuiteCommand command);
}
