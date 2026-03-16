package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request;

import java.util.List;

/**
 * 테스트 스위트에 연결된 테스트 케이스 구성을 전체 교체하기 위한 웹 요청 DTO.
 *
 * @param testCaseIds 최종 연결할 테스트 케이스 식별자 목록
 */
public record ReplaceSuiteTestCasesRequest(
        List<String> testCaseIds) {
}
