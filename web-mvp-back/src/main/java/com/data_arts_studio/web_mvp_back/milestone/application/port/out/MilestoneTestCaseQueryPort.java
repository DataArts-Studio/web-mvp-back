package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestCaseItemResult;

/**
 * 마일스톤 테스트 케이스 조회 전용 포트
 */
public interface MilestoneTestCaseQueryPort {

    /**
     * 마일스톤에 포함된 테스트 케이스 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 케이스 목록
     */
    List<GetMilestoneTestCaseItemResult> findTestCases(String milestoneId);
}
