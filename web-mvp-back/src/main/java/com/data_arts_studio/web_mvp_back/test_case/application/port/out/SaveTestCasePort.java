package com.data_arts_studio.web_mvp_back.test_case.application.port.out;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;

// 테스트 케이스를 저장하는 아웃 포트
public interface SaveTestCasePort {
    void save(TestCase testCase);
}
