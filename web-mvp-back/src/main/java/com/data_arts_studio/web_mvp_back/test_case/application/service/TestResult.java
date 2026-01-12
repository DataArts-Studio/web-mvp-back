package com.data_arts_studio.web_mvp_back.test_case.application.service;

import java.time.LocalDateTime;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestCasePriority;

public record TestResult(
    String testCaseId,
    String projectId,
    String testSuiteId,
    String caseKey,
    String name, 
    String testType,
    String[] tags,
    String steps,
    String expectedResult,
    int estimateMinutes,
    int sortOrder,
    TestCasePriority priority,
    LocalDateTime createdAt,
    LocalDateTime updatedAt

) {
    
}
