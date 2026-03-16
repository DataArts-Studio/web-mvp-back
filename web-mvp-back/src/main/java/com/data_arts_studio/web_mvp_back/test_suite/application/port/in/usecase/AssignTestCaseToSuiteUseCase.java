package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.AssignTestCaseToSuiteCommand;

/**
 * 테스트 스위트에 테스트 케이스를 단건 연결하는 유스케이스.
 */
public interface AssignTestCaseToSuiteUseCase {
    /**
     * 특정 스위트에 테스트 케이스를 단건 연결
     *
     * @param command 프로젝트/스위트/테스트케이스 식별자를 담은 커맨드
     */
    void assignTestCaseToSuite(AssignTestCaseToSuiteCommand command);
}
