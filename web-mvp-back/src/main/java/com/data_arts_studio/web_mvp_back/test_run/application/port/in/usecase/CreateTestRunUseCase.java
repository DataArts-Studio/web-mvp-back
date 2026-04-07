package com.data_arts_studio.web_mvp_back.test_run.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_run.application.port.in.command.CreateTestRunCommand;
import com.data_arts_studio.web_mvp_back.test_run.application.service.result.CreateTestRunResult;

/**
 * 테스트 런 생성 유스케이스
 */
public interface CreateTestRunUseCase {

    /**
     * 단일 마일스톤 범위를 기준으로 테스트 런을 생성
     *
     * @param command 테스트 런 생성 요청 정보
     * @return 생성 결과
     */
    CreateTestRunResult createTestRun(CreateTestRunCommand command);
}
