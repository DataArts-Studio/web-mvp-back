package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;


// // 테스트스위트 저장을 위한 출력 포트
public interface SaveTestSuitePort {
    void save(TestSuite testSuite);
}
