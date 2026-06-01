package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestRunsResult;

/**
 * 마일스톤 기준 테스트 실행 이력 조회 유스케이스
 */
public interface QueryMilestoneTestRunsUseCase {

    /**
     * 마일스톤에 연결된 테스트 실행 이력을 최신순으로 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 실행 목록
     */
    GetMilestoneTestRunsResult getMilestoneTestRuns(String milestoneId);
}
