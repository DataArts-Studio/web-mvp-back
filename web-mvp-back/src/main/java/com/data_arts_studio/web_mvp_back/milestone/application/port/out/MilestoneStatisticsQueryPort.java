package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;
import java.util.Map;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.MilestoneStatisticsResult;

/**
 * 마일스톤 통계 조회 전용 포트
 */
public interface MilestoneStatisticsQueryPort {

    /**
     * 여러 마일스톤의 진행률과 테스트 통계를 조회
     *
     * @param milestoneIds 마일스톤 식별자 목록
     * @return 마일스톤 식별자 기준 통계 맵
     */
    Map<String, MilestoneStatisticsResult> findStatisticsByMilestoneIds(List<String> milestoneIds);
}
