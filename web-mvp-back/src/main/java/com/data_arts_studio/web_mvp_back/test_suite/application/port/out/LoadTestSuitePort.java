package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

import java.util.Optional;

import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

public interface LoadTestSuitePort {
    /**
     * 식별자를 통해 테스트 스위트 도메인을 로드
     * @param testSuiteId
     * @return 검색된 테스트 스위트 (없을 경우 Optional.empty())
     */
    Optional<TestSuite> loadById(TestSuiteId testSuiteId);
}
