package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.UpdateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.UpdateMilestoneResult;

/**
 * 마일스톤 수정 유스케이스
 */
public interface UpdateMilestoneUseCase {

    /**
     * 기존 마일스톤의 기본 정보를 수정
     *
     * @param command 수정 요청 정보
     * @return 수정 결과
     */
    UpdateMilestoneResult updateMilestone(UpdateMilestoneCommand command);
}
