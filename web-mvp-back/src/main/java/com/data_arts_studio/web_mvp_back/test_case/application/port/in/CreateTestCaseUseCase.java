package com.data_arts_studio.web_mvp_back.test_case.application.port.in;
                                                                    
import com.data_arts_studio.web_mvp_back.test_case.application.service.TestResult;

// 테스트 케이스 생성 유스케이스
public interface CreateTestCaseUseCase {
    TestResult createTestCase(CreateTestCaseCommand command);
}