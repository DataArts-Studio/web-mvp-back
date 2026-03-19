package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestCasesResult;

/**
 * 마일스톤에 포함된 테스트 케이스 목록 조회 유스케이스
 */
public interface QueryMilestoneTestCaseLinksUseCase {

    /**
     * 마일스톤에 연결된 테스트 케이스 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 케이스 목록
     */
    GetMilestoneTestCasesResult getMilestoneTestCases(String milestoneId);
}
