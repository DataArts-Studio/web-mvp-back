package com.data_arts_studio.web_mvp_back.milestone.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.CreateMilestoneTestRunCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.CreateMilestoneTestRunUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestoneTestCaseLinksPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveMilestoneTestRunPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveTestCaseRunsPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.CreateMilestoneTestRunResult;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 기준 테스트 실행 생성과 이력 조회를 담당
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MilestoneTestRunService implements CreateMilestoneTestRunUseCase{
    private final LoadMilestonePort loadMilestonePort;
    private final LoadMilestoneTestCaseLinksPort loadMilestoneTestCaseLinksPort;
    private final SaveMilestoneTestRunPort saveMilestoneTestRunPort;
    private final SaveTestCaseRunsPort saveTestCaseRunsPort;
    private final SaveMilestonePort saveMilestonePort;

    /**
     * 마일스톤에 연결된 테스트 케이스 기준으로 테스트 실행과 테스트 케이스 실행 레코드 생성
     *
     * @param command 테스트 실행 생성 요청 정보
     * @return 테스트 실행 생성 결과
     */
    @Override
    public CreateMilestoneTestRunResult createMilestoneTestRun(CreateMilestoneTestRunCommand command) {

        // 요청한 프로젝트에 속한 마일스톤인지 확인
        Milestone milestone = loadMilestonePort.loadById(new MilestoneId(command.milestoneId()))
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
        if (!milestone.getProjectId().getId().equals(command.projectId())) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND);
        }
        // 마일스톤에 연결된 테스트 케이스가 있어야 테스트 실행을 생성할 수 있음
        List<String> testCaseIds = loadMilestoneTestCaseLinksPort.loadTestCaseIdsByMilestone(command.milestoneId());
        if (testCaseIds.isEmpty()) {
            throw new MilestoneBusinessException(MilestoneErrorCode.EMPTY_SCOPE_FOR_TEST_RUN);
        }

        // 테스트 실행과 하위 테스트 케이스 실행 레코드 생성
        String testRunId = saveMilestoneTestRunPort.createTestRun(
                command.projectId(),
                command.milestoneId(),
                command.name(),
                command.description());
        saveTestCaseRunsPort.createTestCaseRuns(testRunId, testCaseIds);
        // 실행 생성 후 마일스톤 상태를 진행 중으로 갱신
        milestone.markInProgress();
        saveMilestonePort.updateMilestone(milestone);

        return CreateMilestoneTestRunResult.builder()
                .id(testRunId)
                .projectId(command.projectId())
                .milestoneId(command.milestoneId())
                .name(command.name())
                .status("NOT_STARTED")
                .build();
    }


}
