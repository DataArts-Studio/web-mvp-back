package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.RemoveTestCaseFromMilestoneCommand;

/**
 * 마일스톤 테스트 케이스 연결을 해제하는 유스케이스
 */
public interface RemoveTestCaseFromMilestoneUseCase {

    /**
     * 마일스톤에서 테스트 케이스 한 건의 연결을 제거
     *
     * @param command 연결 해제 요청 정보
     */
    void removeTestCaseFromMilestone(RemoveTestCaseFromMilestoneCommand command);
}
