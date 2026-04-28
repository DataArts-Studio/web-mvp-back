package com.data_arts_studio.web_mvp_back.test_run.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.test_run.application.loader.MilestoneTestScopeLoader;
import com.data_arts_studio.web_mvp_back.test_run.application.loader.TestRunMilestoneLoader;
import com.data_arts_studio.web_mvp_back.test_run.application.mapper.TestRunResultMapper;
import com.data_arts_studio.web_mvp_back.test_run.application.normalizer.TestRunInputNormalizer;
import com.data_arts_studio.web_mvp_back.test_run.application.port.in.command.CreateTestRunCommand;
import com.data_arts_studio.web_mvp_back.test_run.application.port.in.usecase.CreateTestRunUseCase;
import com.data_arts_studio.web_mvp_back.test_run.application.port.out.SaveTestRunPort;
import com.data_arts_studio.web_mvp_back.test_run.application.service.result.CreateTestRunResult;
import com.data_arts_studio.web_mvp_back.test_run.application.updater.TestRunMilestoneProgressUpdater;
import com.data_arts_studio.web_mvp_back.test_run.application.validator.TestRunCreateValidator;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRun;
import com.data_arts_studio.web_mvp_back.test_run.domain.scope.MilestoneTestScope;
import com.data_arts_studio.web_mvp_back.test_run.domain.service.MilestoneTestScopeFactory;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 런 생성 유스케이스를 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TestRunCreateService implements CreateTestRunUseCase {
    private final SaveTestRunPort saveTestRunPort;
    private final TestRunCreateValidator testRunCreateValidator;
    private final TestRunInputNormalizer testRunInputNormalizer;
    private final TestRunMilestoneLoader testRunMilestoneLoader;
    private final TestRunMilestoneProgressUpdater testRunMilestoneProgressUpdater;
    private final MilestoneTestScopeLoader milestoneTestScopeLoader;
    private final MilestoneTestScopeFactory milestoneTestScopeFactory;
    private final TestRunResultMapper testRunResultMapper;

    /**
     * 하나 이상의 마일스톤 범위를 기준으로 테스트 런을 생성
     *
     * @param command 테스트 런 생성 요청 정보
     * @return 생성 결과
     */
    @Override
    public CreateTestRunResult createTestRun(CreateTestRunCommand command) {
        CreateTestRunCommand normalizedCommand = testRunInputNormalizer.normalize(command);
        testRunCreateValidator.validate(
                normalizedCommand.projectId(),
                normalizedCommand.name(),
                normalizedCommand.milestoneIds());

        // 먼저 선택된 마일스톤을 모두 로드하고, 요청 프로젝트 범위 안에 있는지 차례대로 검증
        List<Milestone> milestones = testRunMilestoneLoader.load(normalizedCommand.milestoneIds());
        milestones.forEach(milestone -> testRunCreateValidator
                .validateMilestoneBelongsToProject(normalizedCommand.projectId(), milestone));

        // 여러 마일스톤에 걸친 스위트/케이스 범위를 하나의 테스트 런 범위로 규합
        MilestoneTestScope milestoneTestScope = milestoneTestScopeFactory.create(
                milestoneTestScopeLoader.load(
                        normalizedCommand.projectId(),
                        normalizedCommand.milestoneIds()));
        
        // 테스트 런 레코드 생성
        TestRun testRun = saveTestRunPort.createTestRun(
                normalizedCommand.projectId(),
                normalizedCommand.milestoneIds(),
                normalizedCommand.name(),
                normalizedCommand.description());

        // 테스트 런과 마일스론 연결 snapshot, 테스트 런과 스위트 연결 snapshot, 테스트 런과 케이스 연결 snapshot 생성
        saveTestRunPort.createTestRunMilestones(
                testRun.getId().getId(),
                normalizedCommand.milestoneIds());
        saveTestRunPort.createTestRunSuites(
                testRun.getId().getId(),
                milestoneTestScope.testRunSuiteScopeItems());
        saveTestRunPort.createTestCaseRuns(
                testRun.getId().getId(),
                milestoneTestScope.testCaseRunScopeItems());
        // 테스트 런 생성과 동시에 선택된 마일스톤 상태를 업데이트
        testRunMilestoneProgressUpdater.markPlannedMilestonesInProgress(milestones);
        
        // 생성된 테스트 런과 연결된 마일스톤 정보를 결과로 반환
        return testRunResultMapper.toCreateTestRunResult(testRun, normalizedCommand.milestoneIds());
    }
}
