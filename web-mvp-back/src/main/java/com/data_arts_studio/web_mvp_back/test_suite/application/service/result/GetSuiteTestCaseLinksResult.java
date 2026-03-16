package com.data_arts_studio.web_mvp_back.test_suite.application.service.result;

import java.util.List;

import lombok.Builder;

/**
 * 특정 테스트 스위트에 연결된 테스트 케이스 ID 목록 조회 결과.
 *
 * @param suiteId 테스트 스위트 식별자
 * @param testCaseIds 현재 스위트에 연결된 테스트 케이스 식별자 목록
 */
@Builder
public record GetSuiteTestCaseLinksResult(
        String suiteId,
        List<String> testCaseIds) {
}
