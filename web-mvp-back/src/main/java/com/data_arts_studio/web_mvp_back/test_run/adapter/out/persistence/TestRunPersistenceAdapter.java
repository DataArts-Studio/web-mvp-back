package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.mapper.TestRunMapper;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.entity.TestCaseRunJpaEntity;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.entity.TestRunJpaEntity;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.entity.TestRunMilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.entity.TestRunSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.id.TestRunMilestoneJpaId;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.id.TestRunSuiteJpaId;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.repository.TestCaseRunJpaRepository;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.repository.TestRunJpaRepository;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.repository.TestRunMilestoneJpaRepository;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.repository.TestRunSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.test_run.application.port.out.SaveTestRunPort;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRun;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRunId;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestCaseRunStatus;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRunStatus;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestCaseTestRunScopeItem;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.item.TestSuiteTestRunScopeItem;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 런과 테스트 케이스 실행 저장을 담당하는 영속성 어댑터
 */
@Component
@RequiredArgsConstructor
public class TestRunPersistenceAdapter implements SaveTestRunPort {
    // 테스트 런과 관련된 JPA 레포지토리 의존성
    private final TestRunJpaRepository testRunJpaRepository;
    private final TestRunMilestoneJpaRepository testRunMilestoneJpaRepository;
    private final TestRunSuiteJpaRepository testRunSuiteJpaRepository;
    private final TestCaseRunJpaRepository testCaseRunJpaRepository;

    private final TestRunMapper testRunMapper;

    /**
     * 테스트 런 레코드를 생성
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 런 이름
     * @param description 테스트 런 설명
     * @return 저장된 테스트 런 도메인 모델
     */
    @Override
    public TestRun createTestRun(String projectId, String name, String description) {
        TestRun testRun = new TestRun(
                TestRunId.create(),
                new ProjectId(projectId),
                name,
                description,
                TestRunStatus.NOT_STARTED); // 생성 시점의 테스트 런의 상태 
        TestRunJpaEntity savedEntity = testRunJpaRepository.save(testRunMapper.toJpaEntity(testRun));
        return testRunMapper.toDomain(savedEntity);
    }

    /**
     * 테스트 런과 연결된 마일스톤 snapshot을 저장
     *
     * @param testRunId 테스트 런 식별자
     * @param milestoneIds 테스트 런에 연결할 마일스톤 식별자 목록
     */
    @Override
    public void createTestRunMilestones(String testRunId, List<String> milestoneIds) {
        if (milestoneIds == null || milestoneIds.isEmpty()) {
            return;
        }
        UUID testRunUuid = UUID.fromString(testRunId);
        testRunMilestoneJpaRepository.saveAll(milestoneIds.stream()
                .map(UUID::fromString)
                .distinct()
                .map(milestoneId -> new TestRunMilestoneJpaEntity(new TestRunMilestoneJpaId(testRunUuid, milestoneId)))
                .toList());
    }

    /**
     * 테스트 런에 연결된 스위트 snapshot을 저장
     *
     * @param testRunId 테스트 런 식별자
     * @param testRunSuites 테스트 스위트 snapshot 범위 아이템 목록
     */
    @Override
    public void createTestRunSuites(String testRunId, List<TestSuiteTestRunScopeItem> testRunSuites) {
        if (testRunSuites == null || testRunSuites.isEmpty()) {
            return;
        }
        UUID testRunUuid = UUID.fromString(testRunId);
        testRunSuiteJpaRepository.saveAll(testRunSuites.stream()
                .map(TestSuiteTestRunScopeItem::testSuiteId)
                .map(UUID::fromString)
                .distinct()
                .map(suiteId -> new TestRunSuiteJpaEntity(new TestRunSuiteJpaId(testRunUuid, suiteId)))
                .toList());
    }

    /**
     * 테스트 런에 포함된 테스트 케이스 실행 레코드를 저장
     *
     * @param testRunId 테스트 런 식별자
     * @param testCaseRuns 테스트 케이스 실행 범위 아이템 목록
     */
    @Override
    public void createTestCaseRuns(String testRunId, List<TestCaseTestRunScopeItem> testCaseRuns) {
        if (testCaseRuns == null || testCaseRuns.isEmpty()) {
            return;
        }
        OffsetDateTime now = OffsetDateTime.now();
        List<TestCaseRunJpaEntity> entities = testCaseRuns.stream()
                .map(testCaseRun -> new TestCaseRunJpaEntity(
                        UUID.randomUUID(),
                        UUID.fromString(testRunId),
                        UUID.fromString(testCaseRun.testCaseId()),
                        TestCaseRunStatus.UNTESTED.getDbValue(),
                        null,
                        null,
                        testCaseRun.sourceType(),
                        testCaseRun.sourceId() == null ? null : UUID.fromString(testCaseRun.sourceId()),
                        now,
                        now))
                .toList();
        testCaseRunJpaRepository.saveAll(entities);
    }
}
