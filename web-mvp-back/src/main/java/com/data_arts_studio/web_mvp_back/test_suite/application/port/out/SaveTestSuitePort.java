package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;

public interface SaveTestSuitePort {
    void save(TestSuite testSuite);
}
