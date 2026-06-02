package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql;

/**
 * 마일스톤 테스트 런 조회 native query 조립 클래스.
 *
 * <p>이 클래스는 {@link MilestoneSqlFragments}를 조합하여
 * 특정 마일스톤과 연관된 테스트 런 목록 및 진척도 통계를 조회하는 SQL을 생성함.</p>
 */
public final class MilestoneTestRunSql {

    private MilestoneTestRunSql() {
    }

    /**
     * 마일스톤에 연결된 테스트 런 목록과 각 테스트 런의 진행률 계산용 집계 데이터를 조회하는 쿼리를 생성함.
     *
     * <p><strong>조회 결과 포함 항목:</strong></p>
     * <ul>
     * <li>테스트 런 기본 정보 (ID, 이름, 생성일시, 상태)</li>
     * <li>해당 테스트 런의 전체 케이스 수({@code total_count}) 및 완료된 케이스 수({@code completed_count})</li>
     * </ul>
     * <p>보관 처리된 테스트 런은 제외되며, 최근 생성된 순서({@code created_at DESC})로 정렬됨.</p>
     *
     * @return 파라미터 템플릿이 결합된 완결된 SQL 문자열
     * @see MilestoneSqlFragments#testRunCaseStatsSubquery()
     */
    public static String findTestRunsByMilestoneId() {
        return """
                SELECT tr.id,
                       tr.name,
                       tr.created_at,
                       tr.status,
                       COALESCE(stats.total_count, 0) AS total_count,
                       COALESCE(stats.completed_count, 0) AS completed_count
                FROM test_runs tr
                JOIN test_run_milestones trm ON trm.test_run_id = tr.id
                LEFT JOIN (
                    %s
                ) stats ON stats.test_run_id = tr.id
                WHERE trm.milestone_id = :milestoneId
                  AND tr.archived_at IS NULL
                ORDER BY tr.created_at DESC
                """.formatted(MilestoneSqlFragments.testRunCaseStatsSubquery());
    }
}