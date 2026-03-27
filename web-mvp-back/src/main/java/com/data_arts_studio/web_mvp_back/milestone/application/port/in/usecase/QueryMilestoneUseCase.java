package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneDetailResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetProjectMilestoneResult;

/**
 * 마일스톤 조회 유스케이스
 */
public interface QueryMilestoneUseCase {

    /**
     * 프로젝트에 속한 활성 마일스톤 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 마일스톤 목록
     */
    GetProjectMilestoneResult getProjectMilestones(String projectId);

    /**
     * 마일스톤 상세 정보 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 상세 정보
     */
    GetMilestoneDetailResult getMilestoneDetail(String milestoneId);
}
