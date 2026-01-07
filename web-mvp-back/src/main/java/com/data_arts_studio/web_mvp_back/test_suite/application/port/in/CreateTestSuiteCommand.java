package com.data_arts_studio.web_mvp_back.test_suite.application.port.in;


public record CreateTestSuiteCommand(
    String projectId,
    String name
    // ,String description 추 후 필요 시 추가 가능

) {
    
}
