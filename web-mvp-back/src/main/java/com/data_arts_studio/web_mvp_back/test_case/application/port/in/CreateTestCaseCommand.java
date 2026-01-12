package com.data_arts_studio.web_mvp_back.test_case.application.port.in;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestCasePriority;


// 테스트 케이스를 생성하기 위한 커맨드 객체
public record CreateTestCaseCommand(
    String projectId,
    String testSuiteId,
    String name,
    String testType,
    String[] tags,
    String steps,
    String expectedResult,
    int estimateMinutes,
    int sortOrder,
    TestCasePriority priority

) {}
