package com.data_arts_studio.web_mvp_back.milestone.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestCaseQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestSuiteQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneDetailResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetProjectMilestoneResult;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 조회 유스케이스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilestoneQueryService implements QueryMilestoneUseCase {
    private final MilestoneQueryPort milestoneQueryPort;
    private final MilestoneTestCaseQueryPort milestoneTestCaseQueryPort;
    private final MilestoneTestSuiteQueryPort milestoneTestSuiteQueryPort;

    @Override
    /**
     * 프로젝트 마일스톤 목록 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 프로젝트 마일스톤 목록 결과
     */
    public GetProjectMilestoneResult getProjectMilestones(String projectId) {
        return GetProjectMilestoneResult.builder()
                .items(milestoneQueryPort.findAllByProject(projectId))
                .build();
    }

    @Override
    /**
     * 마일스톤 상세 정보 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 상세 결과
     */
    public GetMilestoneDetailResult getMilestoneDetail(String milestoneId) {
        GetMilestoneDetailResult baseResult = milestoneQueryPort.findDetail(milestoneId)
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
        // TODO(test-run): 테스트 런 상세 조회 기능이 준비되면 전용 조회 포트를 연결해 이력을 함께 반환할 것.
        return GetMilestoneDetailResult.builder()
                .id(baseResult.id())
                .projectId(baseResult.projectId())
                .name(baseResult.name())
                .description(baseResult.description())
                .status(baseResult.status())
                .startDate(baseResult.startDate())
                .endDate(baseResult.endDate())
                .progressPercent(baseResult.progressPercent())
                .completedCaseCount(baseResult.completedCaseCount())
                .totalCaseCount(baseResult.totalCaseCount())
                .testRunCount(baseResult.testRunCount())
                .createdAt(baseResult.createdAt())
                .updatedAt(baseResult.updatedAt())
                .testCases(milestoneTestCaseQueryPort.findTestCases(milestoneId))
                .testSuites(milestoneTestSuiteQueryPort.findTestSuites(milestoneId))
                .testRuns(List.of())
                .build();
    }
}
