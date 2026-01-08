package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response;

import java.time.LocalDateTime;

public record CreateTestSuiteResponse(
    String id,
    String projectId,
    String name,
    //  String description,
    LocalDateTime createdAt
) {
} 