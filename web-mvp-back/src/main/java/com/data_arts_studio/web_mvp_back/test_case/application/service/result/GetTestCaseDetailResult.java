package com.data_arts_studio.web_mvp_back.test_case.application.service.result;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record GetTestCaseDetailResult(
    String id,
    String projectId,
    String caseKey,
    String resultStatus,
    String name,
    String testSuiteId,
    String testSuiteName,
    String testType,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    List<String> tags,
    String preCondition,
    String steps,
    String expectedResult
) {
}
