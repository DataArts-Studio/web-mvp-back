package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response;

import java.time.OffsetDateTime;

public record GetProjectTestSuiteItemResponse(
    String suiteId,
    String name,
    String type,
    int testCaseCount,
    String milestoneName,
    OffsetDateTime lastExecutedAt,
    int executionCount
) {
}
