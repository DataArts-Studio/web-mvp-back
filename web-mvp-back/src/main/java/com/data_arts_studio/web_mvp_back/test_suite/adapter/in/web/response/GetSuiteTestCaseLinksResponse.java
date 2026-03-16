package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response;

import java.util.List;

/**
 * 특정 테스트 스위트에 연결된 테스트 케이스 ID 목록 응답 DTO.
 *
 * @param suiteId 테스트 스위트 식별자
 * @param testCaseIds 현재 연결된 테스트 케이스 식별자 목록
 */
public record GetSuiteTestCaseLinksResponse(
        String suiteId,
        List<String> testCaseIds) {
}
