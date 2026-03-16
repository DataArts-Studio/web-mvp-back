package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request;

/**
 * 테스트 스위트에 테스트 케이스 1건을 연결하기 위한 웹 요청 DTO.
 *
 * @param testCaseId 연결할 테스트 케이스 식별자
 */
public record AssignTestCaseToSuiteRequest(
        String testCaseId
) {}
