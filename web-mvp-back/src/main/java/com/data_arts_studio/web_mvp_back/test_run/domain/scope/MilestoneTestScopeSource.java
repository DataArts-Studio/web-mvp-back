package com.data_arts_studio.web_mvp_back.test_run.domain.scope;

import java.util.List;
import java.util.Map;

import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;

/**
 * 마일스톤 기준 테스트 범위 생성을 위해 로드한 원본 데이터 묶음
 *
 * @param projectId 대상 프로젝트 식별자
 * @param milestoneIds 대상 마일스톤 식별자 목록
 * @param linkedSuites 마일스톤에 연결된 테스트 스위트 원본 목록
 * @param suiteTestCaseIdsBySuiteId 스위트별 연결된 테스트 케이스 식별자 목록
 * @param milestoneIdByTestCaseId 마일스톤에 직접 연결된 테스트 케이스별 출처 마일스톤 식별자
 */
public record MilestoneTestScopeSource(
        String projectId,
        List<String> milestoneIds,
        List<TestSuite> linkedSuites,
        Map<String, List<String>> suiteTestCaseIdsBySuiteId,
        Map<String, String> milestoneIdByTestCaseId) {
}
