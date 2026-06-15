package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql;

/**
 * 마일스톤 통계 조회 native query 조립 클래스.
 *
 * <p>이 클래스는 {@link MilestoneSqlFragments}를 조합하여
 * 여러 마일스톤의 종합 통계 지표(테스트 케이스 진척도, 연결된 테스트 런 수)를 일괄 조회하는 SQL을 생성함.</p>
 */
public final class MilestoneStatisticsSql {

    private MilestoneStatisticsSql() {
    }

    /**
     * 지정된 여러 마일스톤의 테스트 케이스 진행률 데이터와 테스트 런 총 개수를 조회하는 쿼리를 생성함.
     *
     * <p><strong>조회 결과 포함 항목:</strong></p>
     * <ul>
     * <li>대상 마일스톤 ID ({@code milestone_id})</li>
     * <li>마일스톤별 전체 테스트 케이스 수 ({@code total_count})</li>
     * <li>마일스톤별 완료(pass, fail, blocked)된 테스트 케이스 수 ({@code completed_count})</li>
     * <li>마일스톤별 연결된 활성 테스트 런 총 개수 ({@code test_run_count})</li>
     * </ul>
     * <p>선택된 마일스톤({@code selected_milestones})을 드라이빙 테이블로 삼아, 
     * 케이스 통계({@code case_stats})와 런 통계({@code run_stats})를 각각 {@code LEFT JOIN}함으로써 
     * 데이터가 없는 마일스톤도 기본값 0으로 안전하게 조회됨.</p>
     *
     * @return 파라미터 템플릿이 결합된 완결된 SQL 문자열
     * @see MilestoneSqlFragments#selectedMilestonesCte()
     * @see MilestoneSqlFragments#targetTestCasesForMultipleMilestonesCte()
     * @see MilestoneSqlFragments#milestoneCaseStatsCte()
     * @see MilestoneSqlFragments#milestoneRunStatsSubquery()
     */
    public static String findStatisticsByMilestoneIds() {
        return """
                WITH selected_milestones AS (
                    %s
                ),
                target_test_cases AS (
                    %s
                ),
                case_stats AS (
                    %s
                )
                SELECT selected.milestone_id,
                       COALESCE(case_stats.total_count, 0) AS total_count,
                       COALESCE(case_stats.completed_count, 0) AS completed_count,
                       COALESCE(run_stats.test_run_count, 0) AS test_run_count
                FROM selected_milestones selected
                LEFT JOIN case_stats ON case_stats.milestone_id = selected.milestone_id
                LEFT JOIN (
                    %s
                ) run_stats ON run_stats.milestone_id = selected.milestone_id
                """.formatted(
                MilestoneSqlFragments.selectedMilestonesCte(),
                MilestoneSqlFragments.targetTestCasesForMultipleMilestonesCte(),
                MilestoneSqlFragments.milestoneCaseStatsCte(),
                MilestoneSqlFragments.milestoneRunStatsSubquery());
    }
}