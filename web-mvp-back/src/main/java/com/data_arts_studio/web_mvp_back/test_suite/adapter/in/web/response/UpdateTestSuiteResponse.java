package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response;

// 웹 계층의 응답 DTO
public record UpdateTestSuiteResponse(
    String id,
    String projectId,
    String name,
    String description
) {
}
