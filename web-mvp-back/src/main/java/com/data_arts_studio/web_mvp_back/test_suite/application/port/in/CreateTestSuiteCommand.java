package com.data_arts_studio.web_mvp_back.test_suite.application.port.in;

import lombok.Builder;


@Builder
public record CreateTestSuiteCommand(
    String projectId,
    String name,
    String description
) {}
