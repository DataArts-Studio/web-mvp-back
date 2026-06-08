package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql.MilestoneTestRunSql;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestRunQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestRunItemResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 테스트 실행 조회 전용 read model 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneTestRunQueryAdapter implements MilestoneTestRunQueryPort {
    private final EntityManager entityManager;

    /**
     * 마일스톤에 연결된 테스트 런 목록을 최신순으로 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 런 목록
     */
    @Override
    public List<GetMilestoneTestRunItemResult> findTestRuns(String milestoneId) {
        Query query = entityManager.createNativeQuery(MilestoneTestRunSql.findTestRunsByMilestoneId());
        query.setParameter("milestoneId", UUID.fromString(milestoneId));

        List<?> rawRows = query.getResultList();
        List<GetMilestoneTestRunItemResult> items = new ArrayList<>();
        for (Object rawRow : rawRows) {
            Object[] row = (Object[]) rawRow;
            int totalCount = ((Number) row[4]).intValue();
            int completedCount = ((Number) row[5]).intValue();
            items.add(GetMilestoneTestRunItemResult.builder()
                    .id(row[0].toString())
                    .name((String) row[1])
                    .createdAt(toOffsetDateTime((Timestamp) row[2]))
                    .status((String) row[3])
                    .progressPercent(calculateProgressPercent(totalCount, completedCount))
                    .build());
        }
        return items;
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

    /**
     * DB timestamp를 UTC 기준 OffsetDateTime으로 변환
     *
     * @param timestamp DB timestamp
     * @return 변환된 OffsetDateTime
     */
    private OffsetDateTime toOffsetDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant().atOffset(ZoneOffset.UTC);
    }
}
