package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestSuitesResult;

/**
 * 마일스톤에 연결된 테스트 스위트 목록 조회 유스케이스
 */
public interface QueryMilestoneTestSuiteLinksUseCase {

    /**
     * 마일스톤에 연결된 테스트 스위트 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 목록
     */
    GetMilestoneTestSuitesResult getMilestoneTestSuites(String milestoneId);
}
