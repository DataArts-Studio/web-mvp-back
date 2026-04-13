package com.data_arts_studio.web_mvp_back.test_run.domain.scope;

import java.util.List;

import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestCaseTestRunScopeItem;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestSuiteTestRunScopeItem;

/**
 * 마일스톤 기준 테스트 런 생성 범위를 표현하는 모델
 *
 * @param testRunSuiteScopeItems 마일스톤에 연결된 활성 테스트 스위트 snapshot 범위 아이템 목록
 * @param testCaseRunScopeItems 테스트 케이스 실행 생성 범위 아이템 목록
 */
public record MilestoneTestScope(
        List<TestSuiteTestRunScopeItem> testRunSuiteScopeItems,
        List<TestCaseTestRunScopeItem> testCaseRunScopeItems) {
}
