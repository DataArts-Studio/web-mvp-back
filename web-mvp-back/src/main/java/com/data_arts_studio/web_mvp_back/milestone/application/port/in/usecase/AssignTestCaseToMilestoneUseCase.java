package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.AssignTestCaseToMilestoneCommand;

/**
 * 마일스톤에 테스트 케이스를 연결하는 유스케이스
 */
public interface AssignTestCaseToMilestoneUseCase {

    /**
     * 마일스톤에 테스트 케이스 한 건을 연결
     *
     * @param command 연결 요청 정보
     */
    void assignTestCaseToMilestone(AssignTestCaseToMilestoneCommand command);
}
