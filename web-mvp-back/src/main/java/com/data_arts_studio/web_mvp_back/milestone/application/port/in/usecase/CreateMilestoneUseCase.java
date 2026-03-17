package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.CreateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.CreateMilestoneResult;

/**
 * 마일스톤 생성 유스케이스
 */
public interface CreateMilestoneUseCase {

    /**
     * 새로운 마일스톤을 생성
     *
     * @param command 생성 요청 정보
     * @return 생성 결과
     */
    CreateMilestoneResult createMilestone(CreateMilestoneCommand command);
}
