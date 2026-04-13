package com.data_arts_studio.web_mvp_back.test_run.domain.scope.item;

/**
 * 테스트 런 범위에 포함된 테스트 케이스 실행 아이템 정보
 *
 * @param testCaseId 테스트 케이스 식별자
 * @param sourceType 실행 포함 출처 유형
 * @param sourceId 실행 포함 출처 식별자
 */
public record TestCaseTestRunScopeItem(
        String testCaseId,
        String sourceType,
        String sourceId) {
}
