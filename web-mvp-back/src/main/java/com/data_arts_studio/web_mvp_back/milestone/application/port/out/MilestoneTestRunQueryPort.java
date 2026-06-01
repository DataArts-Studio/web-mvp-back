package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestRunItemResult;

/**
 * 마일스톤 테스트 실행 조회 전용 포트
 */
public interface MilestoneTestRunQueryPort {

    /**
     * 마일스톤 기반 테스트 실행 이력을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 실행 목록
     */
    List<GetMilestoneTestRunItemResult> findTestRuns(String milestoneId);
}
