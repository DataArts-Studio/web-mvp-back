package com.data_arts_studio.web_mvp_back.test_case.application.port.in.command;

/**
 * 아카이브된 테스트 케이스를 복원하기 위한 커맨드.
 *
 * @param projectId  복원할 테스트 케이스가 소속된 프로젝트 식별자
 * @param testCaseId 복원 대상 테스트 케이스 식별자
 */
public record RestoreTestCaseCommand(
    String projectId,
    String testCaseId
) {
    
}
