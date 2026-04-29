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
 * 마일스톤 기준 테스트 범위 생성을 위해 필요한 원본 데이터를 로드하는 컴포넌트.
 *
 * <p>선택된 마일스톤을 기준으로 다음 데이터를 수집</p>
 * <ul>
 *     <li>마일스톤에 연결된 테스트 스위트</li>
 *     <li>각 스위트에 포함된 테스트 케이스 식별자</li>
 *     <li>마일스톤에 직접 연결된 테스트 케이스 식별자</li>
 * </ul>
 *
 * <p>중복 제거와 최종 테스트 런 범위 확정은 {@code MilestoneTestScopeFactory}에서 처리</p>
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
        List<TestSuite> linkedSuites = new ArrayList<>(); //선택된 마일스톤들에 연결된 테스트 스위트 목록
        Map<String, String> milestoneIdBySuiteId = new LinkedHashMap<>(); // 스위트가 어떤 마일스톤을 통해 실행 범위에 들어왔는지 추적
        Map<String, List<String>> suiteTestCaseIdsBySuiteId = new LinkedHashMap<>(); // 스위트에 포함된 테스트 케이스 ID 목록
        Map<String, String> milestoneIdByTestCaseId = new LinkedHashMap<>(); // 테스트 케이스가 직접 연결된 마일스톤 ID

        // 마일스톤 하나를 처리하는 로직은 그대로 두고, 선택된 여러 마일스톤의 원본 데이터를 차례대로 규합
        // TODO(performance): 마일스톤/스위트 수가 많아지면 반복 조회가 늘어나므로 bulk 조회 포트로 전환
        for (String milestoneId : milestoneIds) {
            // 마일스톤 하나를 기준으로 테스트 범위 생성 원본 데이터 로드 
            MilestoneTestScopeSource singleSource = loadSingle(projectId, milestoneId);
            // 같은 키 (같은 스위트나 케이스가 여러 마일스톤에 걸쳐 선택된 경우) -> 먼저 나온 마일스톤 기준으로 범위에 포함시키고 중복된 스위트, 케이스는 무시 
            linkedSuites.addAll(singleSource.linkedSuites());
            singleSource.milestoneIdBySuiteId().forEach(milestoneIdBySuiteId::putIfAbsent);
            singleSource.suiteTestCaseIdsBySuiteId().forEach(suiteTestCaseIdsBySuiteId::putIfAbsent);
            singleSource.milestoneIdByTestCaseId().forEach(milestoneIdByTestCaseId::putIfAbsent);
        }

        return new MilestoneTestScopeSource(
                projectId,
                List.copyOf(milestoneIds),
                linkedSuites,
                milestoneIdBySuiteId,
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
        // milestone_test_suites 연결을 기준으로 스위트 원본을 조회
        List<TestSuite> linkedSuites = loadMilestoneTestSuiteLinksPort.loadTestSuiteIdsByMilestone(milestoneId).stream()
                .map(suiteId -> loadTestSuitePort.loadById(new TestSuiteId(suiteId)))
                .flatMap(Optional::stream)
                .toList();

        Map<String, String> milestoneIdBySuiteId = new LinkedHashMap<>();
        Map<String, List<String>> suiteTestCaseIdsBySuiteId = new LinkedHashMap<>();
        for (TestSuite suite : linkedSuites) {
            // 스위트 경유 케이스도 마일스톤 기준 실행 범위로 기록하기 위해 출처 마일스톤을 보관
            milestoneIdBySuiteId.putIfAbsent(suite.getId().getId(), milestoneId);
            suiteTestCaseIdsBySuiteId.put(
                    suite.getId().getId(),
                    loadSuiteTestCaseLinksPort.loadTestCaseIdsBySuite(suite.getProjectId().getId(), suite.getId().getId()));
        }

        // milestone_test_cases 연결을 기준으로 마일스톤에 직접 포함된 케이스를 조회
        Map<String, String> milestoneIdByTestCaseId = new LinkedHashMap<>();
        loadMilestoneTestCaseLinksPort.loadTestCaseIdsByMilestone(milestoneId)
                .forEach(testCaseId -> milestoneIdByTestCaseId.putIfAbsent(testCaseId, milestoneId));

        return new MilestoneTestScopeSource(
                projectId,
                List.of(milestoneId),
                linkedSuites,
                milestoneIdBySuiteId,
                suiteTestCaseIdsBySuiteId,
                milestoneIdByTestCaseId);
    }
}
