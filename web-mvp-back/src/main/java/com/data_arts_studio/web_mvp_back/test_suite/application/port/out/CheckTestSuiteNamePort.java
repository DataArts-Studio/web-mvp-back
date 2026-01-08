package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

public interface CheckTestSuiteNamePort {
     boolean isTestSuiteNameDuplicated(String projectId, String name);
}
