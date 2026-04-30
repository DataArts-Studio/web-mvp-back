package com.data_arts_studio.web_mvp_back.milestone.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.AssignTestCaseToMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.RemoveTestCaseFromMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ReplaceMilestoneTestCasesCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.AssignTestCaseToMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestCaseLinksUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.RemoveTestCaseFromMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.ReplaceMilestoneTestCasesUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.AssignTestCaseToMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestCaseQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.RemoveTestCaseFromMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.ReplaceMilestoneTestCasesPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestCasesResult;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤과 테스트 케이스 범위 링크 관리 유스케이스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MilestoneTestCaseLinkService implements AssignTestCaseToMilestoneUseCase,
        ReplaceMilestoneTestCasesUseCase,
        RemoveTestCaseFromMilestoneUseCase,
        QueryMilestoneTestCaseLinksUseCase {

    private final AssignTestCaseToMilestonePort assignTestCaseToMilestonePort;
    private final ReplaceMilestoneTestCasesPort replaceMilestoneTestCasesPort;
    private final RemoveTestCaseFromMilestonePort removeTestCaseFromMilestonePort;
    private final LoadMilestonePort loadMilestonePort;
    private final MilestoneTestCaseQueryPort milestoneTestCaseQueryPort;

    /**
     * 마일스톤에 테스트 케이스 한 건 연결
     *
     * @param command 연결 요청 정보
     */
    @Override
    public void assignTestCaseToMilestone(AssignTestCaseToMilestoneCommand command) {
        assignTestCaseToMilestonePort.assign(command.milestoneId(), command.testCaseId());
    }

    /**
     * 마일스톤 테스트 범위를 전달된 목록으로 전체 교체
     *
     * @param command 범위 교체 요청 정보
     */
    @Override
    public void replaceMilestoneTestCases(ReplaceMilestoneTestCasesCommand command) {
        replaceMilestoneTestCasesPort.replace(command.milestoneId(), command.testCaseIds());
    }

    /**
     * 마일스톤에서 테스트 케이스 연결 한 건 제거
     *
     * @param command 연결 해제 요청 정보
     */
    @Override
    public void removeTestCaseFromMilestone(RemoveTestCaseFromMilestoneCommand command) {
        removeTestCaseFromMilestonePort.remove(command.milestoneId(), command.testCaseId());
    }

    /**
     * 마일스톤에 포함된 테스트 케이스 목록 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 케이스 목록 결과
     */
    @Override
    @Transactional(readOnly = true)
    public GetMilestoneTestCasesResult getMilestoneTestCases(String milestoneId) {
        loadMilestonePort.loadById(new MilestoneId(milestoneId))
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
        return GetMilestoneTestCasesResult.builder()
                .milestoneId(milestoneId)
                .items(milestoneTestCaseQueryPort.findTestCases(milestoneId))
                .build();
    }
}
