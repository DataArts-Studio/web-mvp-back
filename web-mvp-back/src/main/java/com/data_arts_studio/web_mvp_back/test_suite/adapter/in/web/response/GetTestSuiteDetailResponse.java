package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response;

import java.time.OffsetDateTime;

public record GetTestSuiteDetailResponse(
    String suiteId,
    String name,
    String description,
    OffsetDateTime createdAt,
    int testCaseCount,
    int executionCount,
    double lastPassRate
) {
}
