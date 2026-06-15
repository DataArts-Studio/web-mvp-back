package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql.MilestoneTestSuiteSql;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestSuiteQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestSuiteItemResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 테스트 스위트 조회 전용 read model 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneTestSuiteQueryAdapter implements MilestoneTestSuiteQueryPort {
    private final EntityManager entityManager;

    @Override
    /**
     * 특정 마일스톤에 연결된 테스트 스위트 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 목록 조회 결과
     */
    public List<GetMilestoneTestSuiteItemResult> findTestSuites(String milestoneId) {
        Query query = entityManager.createNativeQuery(MilestoneTestSuiteSql.findTestSuitesByMilestoneId());
        query.setParameter("milestoneId", UUID.fromString(milestoneId));

        List<?> rawRows = query.getResultList();
        List<GetMilestoneTestSuiteItemResult> items = new ArrayList<>();
        for (Object rawRow : rawRows) {
            Object[] row = (Object[]) rawRow;
            items.add(GetMilestoneTestSuiteItemResult.builder()
                    .id(row[0].toString())
                    .name((String) row[1])
                    .description((String) row[2])
                    .linkedTestCaseCount(((Number) row[3]).intValue())
                    .build());
        }
        return items;
    }
}
