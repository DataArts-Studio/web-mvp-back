package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.CreateMilestoneTestRunCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.CreateMilestoneTestRunResult;

/**
 * 마일스톤 기반 테스트 실행 생성 유스케이스
 */
public interface CreateMilestoneTestRunUseCase {

    /**
     * 마일스톤에 연결된 테스트 케이스를 기준으로 테스트 실행을 생성
     *
     * @param command 테스트 실행 생성 요청 정보
     * @return 생성 결과
     */
    CreateMilestoneTestRunResult createMilestoneTestRun(CreateMilestoneTestRunCommand command);
}
