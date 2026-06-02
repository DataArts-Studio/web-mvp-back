package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql.MilestoneTestCaseSql;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestCaseQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestCaseItemResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 테스트 케이스 조회 전용  어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneTestCaseQueryAdapter implements MilestoneTestCaseQueryPort {
    private final EntityManager entityManager;

    /**
     * 마일스톤에 포함된 테스트 케이스 목록을 조회
     *
     * 직접 연결된 테스트 케이스와 마일스톤에 연결된 테스트 스위트에 포함된 테스트 케이스를
     * 합집합으로 계산한 뒤, 각 테스트 케이스의 최신 실행 결과 상태를 함께 반환
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 테스트 케이스 목록
     */
    @Override
    public List<GetMilestoneTestCaseItemResult> findTestCases(String milestoneId) {
        Query query = entityManager.createNativeQuery(MilestoneTestCaseSql.findTestCasesByMilestoneId());
        query.setParameter("milestoneId", UUID.fromString(milestoneId));

        List<?> rawRows = query.getResultList();
        List<GetMilestoneTestCaseItemResult> items = new ArrayList<>();
        for (Object rawRow : rawRows) {
            Object[] row = (Object[]) rawRow;
            items.add(GetMilestoneTestCaseItemResult.builder()
                    .id(row[0].toString())
                    .caseKey((String) row[1])
                    .name((String) row[2])
                    .latestResultStatus(toPresentationResultStatus((String) row[3]))
                    .build());
        }
        return items;
    }

    /**
     * DB 결과 상태를 화면 표시용 상태값으로 변환
     *
     * @param dbStatus DB에 저장된 테스트 결과 상태
     * @return 화면 표시용 테스트 결과 상태
     */
    private String toPresentationResultStatus(String dbStatus) {
        if (dbStatus == null || "untested".equals(dbStatus)) {
            return "notRun";
        }
        return dbStatus;
    }
}
