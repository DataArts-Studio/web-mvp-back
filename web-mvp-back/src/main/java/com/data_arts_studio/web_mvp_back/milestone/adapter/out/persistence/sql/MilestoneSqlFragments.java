package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.sql;

/**
 * 마일스톤(Milestone) 조회 Native Query 전용 SQL 조각(Fragment) 모음집.
 *
 * <p>이 클래스는 마일스톤 통계, 목록 조회 등 복잡한 native query에서 공통으로 재사용되는
 * CTE(Common Table Expressions) 및 서브쿼리, 조인 문을 집중 관리함.</p>
 *
 * <h3>보안 및 주의사항</h3>
 * <ul>
 * <li>SQL Expression 파라미터를 받는 메서드의 경우, 사용자 입력값을 직접 넘기면 SQL Injection 위험이 있음.</li>
 * <li>반드시 어댑터 내부에서 고정된 컬럼명(예: {@code "target.milestone_id"})이나 안전한 표현식만 전달해야 함.</li>
 * </ul>
 *
 * @author Data Arts Studio
 * @since 1.0.0
 */
final class MilestoneSqlFragments {

    private MilestoneSqlFragments() {
    }

    /**
     * <strong>[CTE]</strong> 단일 마일스톤에 대상 테스트 케이스 ID 목록을 집계함.
     *
     * <p><strong>사용 시점:</strong> 특정 마일스톤 1개에 대한 상세 정보나 통계를 조회할 때 사용함.</p>
     * <p><strong>조회 범위:</strong></p>
     * <ol>
     * <li>마일스톤에 직접 연결된 테스트 케이스 ({@code milestone_test_cases})</li>
     * <li>마일스톤에 연결된 테스트 스위트 경유 테스트 케이스 ({@code milestone_test_suites} → {@code suite_test_cases})</li>
     * </ol>
     * <p>위 두 가지 경로로 조회된 테스트 케이스의 중복을 제거({@code UNION})한 결과를 반환함.
     * 단, 보관 처리된 스위트({@code archived_at IS NOT NULL})에 속한 케이스는 제외됨.</p>
     *
     * @return {@code test_case_id} 단일 컬럼을 가지는 SQL 문자열
     * @see #targetTestCasesForMultipleMilestonesCte() 복수 마일스톤 조회 시 대안
     */
    static String targetTestCasesForSingleMilestoneCte() {
        return """
                SELECT mtc.test_case_id
                FROM milestone_test_cases mtc
                WHERE mtc.milestone_id = :milestoneId
                UNION
                SELECT stc.test_case_id
                FROM milestone_test_suites mts
                JOIN test_suites ts ON ts.id = mts.test_suite_id
                 AND ts.archived_at IS NULL
                JOIN suite_test_cases stc ON stc.suite_id = mts.test_suite_id
                WHERE mts.milestone_id = :milestoneId
                """;
    }

    /**
     * <strong>[CTE]</strong> 여러 마일스톤들에 대한 대상 테스트 케이스 ID 목록을 마일스톤별로 집계함.
     *
     * <p><strong>사용 시점:</strong> 마일스톤 목록 조회나 대시보드 등 여러 마일스톤의 통계를 한 번에 배치 조회할 때 사용함.</p>
     * <p>기본적인 집계 매커니즘은 {@link #targetTestCasesForSingleMilestoneCte()}와 동일하나,
     * 결과 집합에 어떤 마일스톤의 케이스인지를 구분할 수 있도록 {@code milestone_id} 컬럼이 포함됨.</p>
     *
     * @return {@code milestone_id}, {@code test_case_id} 컬럼을 가지는 SQL 문자열
     */
    static String targetTestCasesForMultipleMilestonesCte() {
        return """
                SELECT mtc.milestone_id, mtc.test_case_id
                FROM milestone_test_cases mtc
                WHERE mtc.milestone_id IN (:milestoneIds)
                UNION
                SELECT mts.milestone_id, stc.test_case_id
                FROM milestone_test_suites mts
                JOIN test_suites ts ON ts.id = mts.test_suite_id
                 AND ts.archived_at IS NULL
                JOIN suite_test_cases stc ON stc.suite_id = mts.test_suite_id
                WHERE mts.milestone_id IN (:milestoneIds)
                """;
    }

    /**
     * <strong>[CTE]</strong> 통계 및 조회 대상이 되는 마일스톤 ID 목록을 임시 테이블(CTE)로 고정함.
     *
     * <p><strong>사용 시점:</strong> 대량의 마일스톤 데이터 중 메인 쿼리에서 다룰 대상 테이블의 드라이빙 범위를 제한할 때 사용함.</p>
     *
     * @return {@code milestone_id} 단일 컬럼을 가지는 SQL 문자열
     */
    static String selectedMilestonesCte() {
        return """
                SELECT m.id AS milestone_id
                FROM milestones m
                WHERE m.id IN (:milestoneIds)
                """;
    }

    /**
     * <strong>[JOIN]</strong> 특정 마일스톤 범위 내에서 각 테스트 케이스의 <u>가장 최신 실행 상태</u>를 가로로 결합함.
     *
     * <p><strong>사용 시점:</strong> 테스트 케이스가 여러 테스트 런에서 여러 번 실행되었을 때, 
     * '해당 마일스톤 안에서 가장 마지막으로 실행된 결과'가 무엇인지 파악해야 하는 통계 쿼리에 사용함.</p>
     * <p><strong>주의사항:</strong> 성능 최적화를 위해 {@code LEFT JOIN LATERAL} 문법을 사용하므로, 
     * PostgreSQL 등 해당 문법을 지원하는 DB 환경에서만 정상 작동함. 인자로 전달되는 표현식은 
     * 메인 쿼리에 정의된 별칭(Alias)과 일치해야 함.</p>
     *
     * @param milestoneIdExpression 메인 쿼리에서 매핑할 마일스톤 ID 컬럼 표현식 (예: "target.milestone_id")
     * @param testCaseIdExpression  메인 쿼리에서 매핑할 테스트 케이스 ID 컬럼 표현식 (예: "target.test_case_id")
     * @return {@code latest.status} 컬럼을 제공하는 LATERAL JOIN SQL 문자열
     */
    static String latestMilestoneScopedRunStatusJoin(String milestoneIdExpression, String testCaseIdExpression) {
        return """
                LEFT JOIN LATERAL (
                    SELECT tcr.status
                    FROM test_case_runs tcr
                    JOIN test_runs tr ON tr.id = tcr.test_run_id
                    JOIN test_run_milestones trm ON trm.test_run_id = tr.id
                    WHERE tcr.test_case_id = %s
                      AND trm.milestone_id = %s
                      AND tr.archived_at IS NULL
                    ORDER BY tcr.created_at DESC, tcr.id DESC
                    LIMIT 1
                ) latest ON true
                """.formatted(testCaseIdExpression, milestoneIdExpression);
    }

    /**
     * <strong>[CTE]</strong> 마일스톤별 전체 테스트 케이스 수 및 완료(진행)된 테스트 케이스 수를 집계함.
     *
     * <p><strong>사용 시점:</strong> 마일스톤의 진척도(Progress Bar)나 달성률을 계산할 때 사용함.</p>
     * <p><strong>전제 조건:</strong> 이 CTE를 사용하기 전, 메인 쿼리 상단에 {@code target_test_cases}라는 이름의 
     * 임시 테이블(CTE)이 반드시 먼저 선언되어 있어야 함. ({@link #targetTestCasesForMultipleMilestonesCte()} 등 활용)</p>
     * <p><strong>진척도 판단 기준:</strong></p>
     * <ul>
     * <li>보관 처리된 테스트 케이스({@code archived_at IS NOT NULL})는 집계에서 제외됨.</li>
     * <li>최신 실행 상태({@code status})가 {@code 'pass'}, {@code 'fail'}, {@code 'blocked'} 중 하나라면 완료({@code completed_count})로 간주함.</li>
     * <li>실행 이력이 없거나({@code NULL}), {@code 'untested'} 상태라면 미완료로 취급함.</li>
     * </ul>
     *
     * @return {@code milestone_id}, {@code total_count}, {@code completed_count} 컬럼을 가지는 SQL 문자열
     * @see #latestMilestoneScopedRunStatusJoin(String, String) 내부적으로 이 조인 조각을 결합하여 집계함.
     */
    static String milestoneCaseStatsCte() {
        return """
                SELECT target.milestone_id,
                       COUNT(DISTINCT target.test_case_id) AS total_count,
                       COUNT(DISTINCT CASE WHEN COALESCE(latest.status, 'untested') IN ('pass', 'fail', 'blocked')
                                           THEN target.test_case_id END) AS completed_count
                FROM target_test_cases target
                JOIN test_cases tc ON tc.id = target.test_case_id AND tc.archived_at IS NULL
                %s
                GROUP BY target.milestone_id
                """.formatted(latestMilestoneScopedRunStatusJoin(
                "target.milestone_id",
                "target.test_case_id"));
    }

    /**
     * <strong>[Subquery]</strong> 마일스톤별로 연결된 활성(Active) 테스트 런(Test Run)의 총 개수를 집계함.
     *
     * <p><strong>사용 시점:</strong> 마일스톤 목록이나 상세화면에서 "연결된 테스트 런 개수" 메트릭을 보여줄 때 인라인 뷰나 조인용 서브쿼리로 사용함.</p>
     * <p>보관 처리된 테스트 런({@code archived_at IS NOT NULL})은 카운트에서 제외됨.</p>
     *
     * @return {@code milestone_id}, {@code test_run_count} 컬럼을 가지는 서브쿼리 SQL 문자열
     */
    static String milestoneRunStatsSubquery() {
        return """
                SELECT trm.milestone_id, COUNT(*) AS test_run_count
                FROM test_run_milestones trm
                JOIN test_runs tr ON tr.id = trm.test_run_id
                WHERE tr.archived_at IS NULL
                  AND trm.milestone_id IN (:milestoneIds)
                GROUP BY trm.milestone_id
                """;
    }

    /**
     * <strong>[Subquery]</strong> 테스트 런(Test Run)별 전체 케이스 실행 수와 완료된 케이스 실행 수를 집계함.
     *
     * <p><strong>사용 시점:</strong> 개별 테스트 런 단위의 진척 상황을 파악하여 마일스톤 하위의 런 리스트를 보여줄 때 주로 조합됨.</p>
     * <p>완료 여부는 {@code tcr.status}가 {@code 'pass', 'fail', 'blocked'}에 해당하는지 여부로 판단하며, 
     * 가독성과 성능을 위해 {@code FILTER (WHERE ...)} 문법을 활용함.</p>
     *
     * @return {@code test_run_id}, {@code total_count}, {@code completed_count} 컬럼을 가지는 서브쿼리 SQL 문자열
     */
    static String testRunCaseStatsSubquery() {
        return """
                SELECT tcr.test_run_id,
                       COUNT(*) AS total_count,
                       COUNT(*) FILTER (WHERE tcr.status IN ('pass', 'fail', 'blocked')) AS completed_count
                FROM test_case_runs tcr
                GROUP BY tcr.test_run_id
                """;
    }

    /**
     * <strong>[Subquery]</strong> 테스트 스위트(Test Suite)별로 포함된 테스트 케이스의 총 개수를 집계함.
     *
     * <p><strong>사용 시점:</strong> 마일스톤에 연계된 스위트 목록을 조회할 때, 각 스위트가 몇 개의 케이스를 품고 있는지 표현하기 위해 사용함.</p>
     *
     * @return {@code suite_id}, {@code linked_test_case_count} 컬럼을 가지는 서브쿼리 SQL 문자열
     */
    static String suiteLinkedTestCaseStatsSubquery() {
        return """
                SELECT stc.suite_id,
                       COUNT(*) AS linked_test_case_count
                FROM suite_test_cases stc
                GROUP BY stc.suite_id
                """;
    }
}