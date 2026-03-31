package com.data_arts_studio.web_mvp_back.milestone.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.AssignTestSuiteToMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.RemoveTestSuiteFromMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ReplaceMilestoneTestSuitesCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.AssignTestSuiteToMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestSuiteLinksUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.RemoveTestSuiteFromMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.ReplaceMilestoneTestSuitesUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.AssignTestSuiteToMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestSuiteQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.RemoveTestSuiteFromMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.ReplaceMilestoneTestSuitesPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestSuitesResult;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 테스트 스위트 링크 관리 유스케이스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MilestoneTestSuiteLinkService implements AssignTestSuiteToMilestoneUseCase,
                                                      ReplaceMilestoneTestSuitesUseCase,
                                                      RemoveTestSuiteFromMilestoneUseCase,
                                                      QueryMilestoneTestSuiteLinksUseCase {

                                                        
    private final AssignTestSuiteToMilestonePort assignTestSuiteToMilestonePort;
    private final ReplaceMilestoneTestSuitesPort replaceMilestoneTestSuitesPort;
    private final RemoveTestSuiteFromMilestonePort removeTestSuiteFromMilestonePort;
    private final LoadMilestonePort loadMilestonePort;
    private final MilestoneTestSuiteQueryPort milestoneTestSuiteQueryPort;

    /**
     * 마일스톤에 테스트 스위트 한 건 연결
     *
     * @param command 연결 요청 정보
     */
    @Override
    public void assignTestSuiteToMilestone(AssignTestSuiteToMilestoneCommand command) {
        assignTestSuiteToMilestonePort.assign(command.milestoneId(), command.testSuiteId());
    }

    /**
     * 마일스톤 테스트 스위트 범위를 전달된 목록으로 전체 교체
     *
     * @param command 범위 교체 요청 정보
     */
    @Override
    public void replaceMilestoneTestSuites(ReplaceMilestoneTestSuitesCommand command) {
        replaceMilestoneTestSuitesPort.replace(command.milestoneId(), command.testSuiteIds());
    }

    /**
     * 마일스톤에서 테스트 스위트 연결 한 건 제거
     *
     * @param command 연결 해제 요청 정보
     */
    @Override
    public void removeTestSuiteFromMilestone(RemoveTestSuiteFromMilestoneCommand command) {
        removeTestSuiteFromMilestonePort.remove(command.milestoneId(), command.testSuiteId());
    }

    /**
     * 마일스톤에 연결된 테스트 스위트 목록 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 목록 결과
     */
    @Override
    @Transactional(readOnly = true)
    public GetMilestoneTestSuitesResult getMilestoneTestSuites(String milestoneId) {
        loadMilestonePort.loadById(new MilestoneId(milestoneId))
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
        return GetMilestoneTestSuitesResult.builder()
                .milestoneId(milestoneId)
                .items(milestoneTestSuiteQueryPort.findTestSuites(milestoneId))
                .build();
    }
}
