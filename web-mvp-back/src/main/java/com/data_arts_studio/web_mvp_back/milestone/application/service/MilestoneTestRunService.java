package com.data_arts_studio.web_mvp_back.milestone.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestRunsUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestRunQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestRunsResult;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 기준 테스트 실행 이력 조회를 담당
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilestoneTestRunService implements QueryMilestoneTestRunsUseCase {
    private final LoadMilestonePort loadMilestonePort;
    private final MilestoneTestRunQueryPort milestoneTestRunQueryPort;

    /**
     * 특정 마일스톤에 연결된 테스트 케이스들의 실행 이력 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 실행 이력 조회 결과
     */
    @Override
    public GetMilestoneTestRunsResult getMilestoneTestRuns(String milestoneId) {
        loadMilestonePort.loadById(new MilestoneId(milestoneId))
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
        return GetMilestoneTestRunsResult.builder()
                .milestoneId(milestoneId)
                .items(milestoneTestRunQueryPort.findTestRuns(milestoneId))
                .build();
    }
}
