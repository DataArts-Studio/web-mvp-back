package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request;

// 웹 계층의 요청 DTO
public record UpdateTestSuiteRequest(
    String name,
    String description
) {
}
