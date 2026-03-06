package com.data_arts_studio.web_mvp_back.test_case.application.port.in.command;

/**
 * 테스트 케이스 아카이브 요청 커맨드.
 * 특정 프로젝트 범위에서 대상 테스트 케이스를 아카이브 처리하기 위해 유스케이스에 전달되는 입력 모델
 * @param projectId 프로젝트 식별자
 * @param testCaseId 테스트 케이스 식별자
 */
public record ArchiveTestCaseCommand(
    String projectId,
    String testCaseId
) {
}
