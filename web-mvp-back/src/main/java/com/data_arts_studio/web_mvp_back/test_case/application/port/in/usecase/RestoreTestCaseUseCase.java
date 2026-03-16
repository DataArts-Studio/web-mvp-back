package com.data_arts_studio.web_mvp_back.test_case.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_case.application.port.in.command.RestoreTestCaseCommand;

/**
 * 아카이브된 테스트 케이스를 복원하는 유스케이스.
 */
public interface RestoreTestCaseUseCase {
    /**
     * 지정한 테스트 케이스를 복원
     *
     * @param command 복원 대상 테스트 케이스 식별자를 담은 커맨드
     */
    void restoreTestCase(RestoreTestCaseCommand command);
}
