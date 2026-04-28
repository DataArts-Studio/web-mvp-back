package com.data_arts_studio.web_mvp_back.test_run.application.port.out;

import java.util.List;

import com.data_arts_studio.web_mvp_back.test_run.domain.TestRun;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestCaseTestRunScopeItem;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestSuiteTestRunScopeItem;

/**
 * 테스트 런 저장 포트
 */
public interface SaveTestRunPort {

    /**
     * 테스트 런 레코드를 생성
     *
     * @param projectId 프로젝트 식별자
     * @param milestoneIds 기준 마일스톤 식별자 목록
     * @param name 테스트 런 이름
     * @param description 테스트 런 설명
     * @return 저장된 테스트 런 도메인 모델
     */
    TestRun createTestRun(String projectId, List<String> milestoneIds, String name, String description);

    /**
     * 테스트 런과 마일스톤 연결 snapshot을 생성
     *
     * @param testRunId 테스트 런 식별자
     * @param milestoneIds 테스트 런에 연결할 마일스톤 식별자 목록
     */
    void createTestRunMilestones(String testRunId, List<String> milestoneIds);

    /**
     * 테스트 런에 포함된 스위트 snapshot을 생성
     *
     * @param testRunId 테스트 런 식별자
     * @param testRunSuites 테스트 스위트 snapshot 범위 아이템 목록
     */
    void createTestRunSuites(String testRunId, List<TestSuiteTestRunScopeItem> testRunSuites);

    /**
     * 테스트 런에 포함된 테스트 케이스 실행 레코드를 일괄 생성
     *
     * @param testRunId 테스트 런 식별자
     * @param testCaseRuns 포함할 테스트 케이스 실행 범위 아이템 목록
     */
    void createTestCaseRuns(String testRunId, List<TestCaseTestRunScopeItem> testCaseRuns);
}
