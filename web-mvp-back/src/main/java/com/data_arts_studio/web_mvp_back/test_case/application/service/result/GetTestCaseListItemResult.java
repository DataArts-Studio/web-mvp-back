package com.data_arts_studio.web_mvp_back.test_case.application.service.result;

import java.time.OffsetDateTime;

public record GetTestCaseListItemResult(
    String id,
    String projectId,
    String name,
    OffsetDateTime latestModifiedAt
) {
}
