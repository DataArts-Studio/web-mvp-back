package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command;

import lombok.Builder;

/**
 * 테스트 스위트에 테스트 케이스 1건을 연결하기 위한 커맨드.
 *
 * @param projectId 프로젝트 식별자
 * @param suiteId 테스트 스위트 식별자
 * @param testCaseId 연결할 테스트 케이스 식별자
 */
@Builder
public record AssignTestCaseToSuiteCommand(
        String projectId,
        String suiteId,
        String testCaseId) {
}
