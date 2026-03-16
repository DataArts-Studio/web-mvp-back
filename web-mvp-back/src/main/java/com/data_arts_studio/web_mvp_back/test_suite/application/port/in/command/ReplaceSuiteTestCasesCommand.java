package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command;

import java.util.List;

import lombok.Builder;

/**
 * 테스트 스위트에 연결된 테스트 케이스 집합을 전체 교체하기 위한 커맨드.
 *
 * @param projectId 프로젝트 식별자
 * @param suiteId 테스트 스위트 식별자
 * @param testCaseIds 최종 연결할 테스트 케이스 식별자 목록
 */
@Builder
public record ReplaceSuiteTestCasesCommand(
        String projectId,
        String suiteId,
        List<String> testCaseIds) {
}
