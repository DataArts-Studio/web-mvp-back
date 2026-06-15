package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql;

/**
 * 마일스톤에 연결된 테스트 케이스 조회 native query 조립 클래스.
 *
 * <p>이 클래스는 {@link MilestoneSqlFragments}를 조합하여
 * 특정 마일스톤에 속한 테스트 케이스 목록을 조회하는 SQL을 생성함.</p>
 *
 */
public final class MilestoneTestCaseSql {

    /**
     * 마일스톤에 직접 연결된 테스트 케이스 ID 목록만 조회하는 고정 SQL.
     *
     * <p>스위트 경유 케이스는 포함하지 않으며, 테스트 런 생성 범위를 조립할 때
     * 직접 연결 케이스 출처를 분리해서 추적하기 위한 용도로 사용함.</p>
     */
    public static final String FIND_DIRECT_TEST_CASE_IDS_BY_MILESTONE_ID = """
            SELECT tc.id
            FROM milestone_test_cases mtc
            JOIN test_cases tc ON tc.id = mtc.test_case_id
            WHERE mtc.milestone_id = :milestoneId
              AND tc.archived_at IS NULL
            ORDER BY tc.sort_order ASC, tc.created_at ASC
            """;

    private MilestoneTestCaseSql() {
    }

    /**
     * 단일 마일스톤에 포함된 테스트 케이스와 해당 마일스톤 범위 내의 최신 실행 상태를 조회하는 쿼리를 생성함.
     *
     * <p><strong>조회 결과 포함 항목:</strong></p>
     * <ul>
     * <li>테스트 케이스 기본 정보 (ID, Key, 이름)</li>
     * <li>해당 마일스톤에 연결된 테스트 런들 중 가장 최신의 실행 상태 (이력이 없으면 'untested')</li>
     * </ul>
     * <p>보관 처리된 테스트 케이스는 제외되며, 정렬 순서({@code sort_order}) 및 생성일시({@code created_at}) 순으로 정렬됨.</p>
     *
     * @return 파라미터 템플릿이 결합된 완결된 SQL 문자열
     * @see MilestoneSqlFragments#targetTestCasesForSingleMilestoneCte()
     * @see MilestoneSqlFragments#latestMilestoneScopedRunStatusJoin(String, String)
     */
    public static String findTestCasesByMilestoneId() {
        return """
                WITH target_test_cases AS (
                    %s
                )
                SELECT tc.id,
                       tc.case_key,
                       tc.name,
                       COALESCE(latest.status, 'untested') AS latest_status
                FROM target_test_cases target
                JOIN test_cases tc ON tc.id = target.test_case_id
                %s
                WHERE tc.archived_at IS NULL
                ORDER BY tc.sort_order ASC, tc.created_at ASC
                """.formatted(
                MilestoneSqlFragments.targetTestCasesForSingleMilestoneCte(),
                MilestoneSqlFragments.latestMilestoneScopedRunStatusJoin(
                        ":milestoneId",
                        "tc.id"));
    }

}
