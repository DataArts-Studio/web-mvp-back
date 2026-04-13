package com.data_arts_studio.web_mvp_back.test_run.domain.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.shared.LifecycleStatus;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.MilestoneTestScope;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.MilestoneTestScopeSource;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestCaseTestRunScopeItem;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestSuiteTestRunScopeItem;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;

/**
 * 마일스톤 기준 테스트 범위 생성 규칙을 담당하는 도메인 서비스
 */
@Component
public class MilestoneTestScopeFactory {

    /**
     * 로드된 마일스톤 원본 데이터를 기반으로 최종 테스트 범위를 생성
     *
     * @param source 마일스톤 테스트 범위 생성에 필요한 원본 데이터
     * @return 규칙이 적용된 최종 테스트 범위
     */
    public MilestoneTestScope create(MilestoneTestScopeSource source) {
        // 마일스톤에 연결된 스위트 중 active 상태이면서 동일 프로젝트에 속한 스위트만 범위에 포함
        List<TestSuite> activeSuites = source.linkedSuites().stream()
                .filter(suite -> suite.getArchivedAt() == null)
                .filter(suite -> suite.getLifecycleStatus() == LifecycleStatus.ACTIVE)
                .filter(suite -> suite.getProjectId().getId().equals(source.projectId()))
                // 정렬: sortOrder 기준 오름차순, 동일하면 createdAt, 마지막으로 ID 기준 오름차순
                .sorted(Comparator.comparingInt(TestSuite::getSortOrder)
                        .thenComparing(TestSuite::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(suite -> suite.getId().getId()))
                // 같은 스위트가 여러 마일스톤에 걸쳐 연결되어 있어도
                // 테스트 런 snapshot 에는 스위트 1건만 남긴다.
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                suite -> suite.getId().getId(),
                                Function.identity(),
                                (first, ignored) -> first,
                                LinkedHashMap::new),
                        map -> List.copyOf(map.values())));

        Map<String, TestCaseTestRunScopeItem> scopeItemsByTestCaseId = new LinkedHashMap<>();
        // 스위트에 연결된 테스트 케이스를 먼저 수집해, 중복 시 스위트 경로가 우선되도록 처리
        for (TestSuite suite : activeSuites) {
            source.suiteTestCaseIdsBySuiteId()
                    .getOrDefault(suite.getId().getId(), List.of())
                    .forEach(testCaseId -> scopeItemsByTestCaseId.putIfAbsent(
                            testCaseId,
                            new TestCaseTestRunScopeItem(testCaseId, "suite", suite.getId().getId())));
        }

        // 마일스톤에 직접 연결된 테스트 케이스는 이후에 추가해, 이미 스위트 경로가 있으면 덮어쓰지 않음
        source.milestoneIdByTestCaseId().forEach((testCaseId, milestoneId) -> scopeItemsByTestCaseId.putIfAbsent(
                testCaseId,
                new TestCaseTestRunScopeItem(testCaseId, "milestone", milestoneId)));

        return new MilestoneTestScope(
                // 최종 포함된 활성 스위트를 테스트 런 snapshot 아이템으로 변환
                activeSuites.stream()
                        .map(suite -> new TestSuiteTestRunScopeItem(suite.getId().getId()))
                        .toList(),
                // 중복 제거와 우선순위 규칙이 반영된 테스트 케이스 범위 아이템 목록
                List.copyOf(scopeItemsByTestCaseId.values()));
    }
}
