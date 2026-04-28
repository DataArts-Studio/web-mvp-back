package com.data_arts_studio.web_mvp_back.test_run.application.loader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestoneTestCaseLinksPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestoneTestSuiteLinksPort;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.MilestoneTestScopeSource;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadSuiteTestCaseLinksPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 기준 테스트 범위 생성을 위해 필요한 원본 데이터를 로드하는 컴포넌트
 */
@Component
@RequiredArgsConstructor
public class MilestoneTestScopeLoader {
    // 마일스톤에 연결되어 있는 스위트와 케이스를 로드하는 포트 
    private final LoadMilestoneTestCaseLinksPort loadMilestoneTestCaseLinksPort;
    private final LoadMilestoneTestSuiteLinksPort loadMilestoneTestSuiteLinksPort;
    // 스위트와 케이스의 상세 정보를 로드하는 포트
    private final LoadTestSuitePort loadTestSuitePort;
    private final LoadSuiteTestCaseLinksPort loadSuiteTestCaseLinksPort;

    /**
     * 마일스톤 기준 테스트 범위 생성에 필요한 스위트와 테스트 케이스 원본 데이터를 로드
     *
     * @param projectId 프로젝트 식별자
     * @param milestoneIds 마일스톤 식별자 목록
     * @return 마일스톤 테스트 범위 생성 원본 데이터
     */
    public MilestoneTestScopeSource load(String projectId, List<String> milestoneIds) {
        List<TestSuite> linkedSuites = new ArrayList<>();
        Map<String, List<String>> suiteTestCaseIdsBySuiteId = new LinkedHashMap<>();
        Map<String, String> milestoneIdByTestCaseId = new LinkedHashMap<>();

        // 마일스톤 하나를 처리하는 로직은 그대로 두고, 선택된 여러 마일스톤의 원본 데이터를 차례대로 규합
        for (String milestoneId : milestoneIds) {
            // 마일스톤 하나를 기준으로 테스트 범위 생성 원본 데이터 로드 
            MilestoneTestScopeSource singleSource = loadSingle(projectId, milestoneId);
            // 규합 
            linkedSuites.addAll(singleSource.linkedSuites());
            singleSource.suiteTestCaseIdsBySuiteId().forEach(suiteTestCaseIdsBySuiteId::putIfAbsent);
            singleSource.milestoneIdByTestCaseId().forEach(milestoneIdByTestCaseId::putIfAbsent);
        }

        return new MilestoneTestScopeSource(
                projectId,
                List.copyOf(milestoneIds),
                linkedSuites,
                suiteTestCaseIdsBySuiteId,
                milestoneIdByTestCaseId);
    }

    /**
     * 마일스톤 하나를 기준으로 테스트 범위 생성 원본 데이터를 로드
     *
     * @param projectId 프로젝트 식별자
     * @param milestoneId 마일스톤 식별자
     * @return 단일 마일스톤 테스트 범위 생성 원본 데이터
     */
    private MilestoneTestScopeSource loadSingle(String projectId, String milestoneId) {
        List<TestSuite> linkedSuites = loadMilestoneTestSuiteLinksPort.loadTestSuiteIdsByMilestone(milestoneId).stream()
                .map(suiteId -> loadTestSuitePort.loadById(new TestSuiteId(suiteId)))
                .flatMap(Optional::stream)
                .toList();

        Map<String, List<String>> suiteTestCaseIdsBySuiteId = new LinkedHashMap<>();
        for (TestSuite suite : linkedSuites) {
            suiteTestCaseIdsBySuiteId.put(
                    suite.getId().getId(),
                    loadSuiteTestCaseLinksPort.loadTestCaseIdsBySuite(suite.getProjectId().getId(), suite.getId().getId()));
        }

        Map<String, String> milestoneIdByTestCaseId = new LinkedHashMap<>();
        loadMilestoneTestCaseLinksPort.loadTestCaseIdsByMilestone(milestoneId)
                .forEach(testCaseId -> milestoneIdByTestCaseId.putIfAbsent(testCaseId, milestoneId));

        return new MilestoneTestScopeSource(
                projectId,
                List.of(milestoneId),
                linkedSuites,
                suiteTestCaseIdsBySuiteId,
                milestoneIdByTestCaseId);
    }
}
