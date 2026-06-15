package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql;

/**
 * 마일스톤에 연결된 테스트 스위트 조회 native query 조립 클래스.
 *
 * <p>이 클래스는 {@link MilestoneSqlFragments}를 조합하여
 * 특정 마일스톤에 매핑된 테스트 스위트 목록을 조회하는 SQL을 생성함.</p>
 */
public final class MilestoneTestSuiteSql {

    private MilestoneTestSuiteSql() {
    }

    /**
     * 마일스톤에 연결된 테스트 스위트 목록과 각 스위트에 포함된 테스트 케이스 수를 조회하는 쿼리를 생성
     *
     * <p><strong>조회 결과 포함 항목:</strong></p>
     * <ul>
     * <li>테스트 스위트 기본 정보 (ID, 이름, 설명)</li>
     * <li>각 스위트에 속한 활성 테스트 케이스 총 개수({@code linked_test_case_count})</li>
     * </ul>
     * <p>보관 처리된 테스트 스위트는 제외되며, 정렬 순서({@code sort_order}) 및 생성일시({@code created_at}) 순으로 정렬됨.</p>
     *
     * @return 파라미터 템플릿이 결합된 완결된 SQL 문자열
     * @see MilestoneSqlFragments#suiteLinkedTestCaseStatsSubquery()
     */
    public static String findTestSuitesByMilestoneId() {
        return """
                SELECT ts.id,
                       ts.name,
                       ts.description,
                       COALESCE(stats.linked_test_case_count, 0) AS linked_test_case_count
                FROM milestone_test_suites mts
                JOIN test_suites ts ON ts.id = mts.test_suite_id
                LEFT JOIN (
                    %s
                ) stats ON stats.suite_id = ts.id
                WHERE mts.milestone_id = :milestoneId
                  AND ts.archived_at IS NULL
                ORDER BY ts.sort_order ASC, ts.created_at ASC
                """.formatted(MilestoneSqlFragments.suiteLinkedTestCaseStatsSubquery());
    }
}