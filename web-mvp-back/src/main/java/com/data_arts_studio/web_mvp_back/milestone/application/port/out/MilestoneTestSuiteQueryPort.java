package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestSuiteItemResult;

/**
 * 마일스톤 테스트 스위트 조회 전용 포트
 */
public interface MilestoneTestSuiteQueryPort {

    /**
     * 마일스톤에 연결된 테스트 스위트 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 목록
     */
    List<GetMilestoneTestSuiteItemResult> findTestSuites(String milestoneId);
}
