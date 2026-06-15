package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql.MilestoneStatisticsSql;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneStatisticsQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.MilestoneStatisticsResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 진행률과 테스트 실행 통계를 조회 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneStatisticsQueryAdapter implements MilestoneStatisticsQueryPort {
    private final EntityManager entityManager;

    /**
     * 여러 마일스톤의 진행률과 테스트 실행 통계를 조회
     *
     * @param milestoneIds 마일스톤 식별자 목록
     * @return 마일스톤 식별자 기준 통계 맵
     */
    @Override
    public Map<String, MilestoneStatisticsResult> findStatisticsByMilestoneIds(List<String> milestoneIds) {
        if (milestoneIds.isEmpty()) {
            return Map.of();
        }

        Query query = entityManager.createNativeQuery(MilestoneStatisticsSql.findStatisticsByMilestoneIds());
        query.setParameter("milestoneIds", milestoneIds.stream().map(UUID::fromString).toList());

        List<?> rawRows = query.getResultList();
        Map<String, MilestoneStatisticsResult> statisticsByMilestoneId = new HashMap<>();
        for (Object rawRow : rawRows) {
            Object[] row = (Object[]) rawRow;
            MilestoneStatisticsResult result = toStatisticsResult(row);
            statisticsByMilestoneId.put(result.milestoneId(), result);
        }

        for (String milestoneId : milestoneIds) {
            statisticsByMilestoneId.putIfAbsent(milestoneId, MilestoneStatisticsResult.empty(milestoneId));
        }
        return statisticsByMilestoneId;
    }

    /**
     * native query 한 행을 마일스톤 통계 결과 모델로 변환
     *
     * @param row native query 결과 행
     * @return 마일스톤 통계 결과
     */
    private MilestoneStatisticsResult toStatisticsResult(Object[] row) {
        int totalCount = ((Number) row[1]).intValue();
        int completedCount = ((Number) row[2]).intValue();
        int testRunCount = ((Number) row[3]).intValue();
        return new MilestoneStatisticsResult(
                row[0].toString(),
                totalCount,
                completedCount,
                calculateProgressPercent(totalCount, completedCount),
                testRunCount);
    }

    /**
     * 완료된 테스트 케이스 수를 기준으로 진행률을 계산
     *
     * @param totalCount 전체 테스트 케이스 수
     * @param completedCount 완료된 테스트 케이스 수
     * @return 진행률 퍼센트
     */
    private int calculateProgressPercent(int totalCount, int completedCount) {
        if (totalCount == 0) {
            return 0;
        }
        return (int) Math.round((completedCount * 100.0) / totalCount);
    }
}
