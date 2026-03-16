package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.RemoveTestCaseFromSuiteCommand;

/**
 * 테스트 스위트에서 테스트 케이스 연결을 단건 해제하는 유스케이스.
 */
public interface RemoveTestCaseFromSuiteUseCase {

    /**
     * 특정 스위트에서 테스트 케이스 연결 1건을 제거
     *
     * @param command 프로젝트/스위트/테스트케이스 식별자를 담은 커맨드
     */
    void removeTestCaseFromSuite(RemoveTestCaseFromSuiteCommand command);
}
