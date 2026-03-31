package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneDetailResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestCaseItemResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestRunItemResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestSuiteItemResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetProjectMilestoneItemResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetProjectMilestoneResponse;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneDetailResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetProjectMilestoneResult;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 조회 및 통계 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MilestoneQueryController {
    private final QueryMilestoneUseCase queryMilestoneUseCase;

    /**
     * 특정 프로젝트의 활성 마일스톤 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 마일스톤 목록 응답
     */
    @GetMapping("/projects/{projectId}/milestones")
    public ResponseEntity<GetProjectMilestoneResponse> getProjectMilestones(@PathVariable String projectId) {
        GetProjectMilestoneResult result = queryMilestoneUseCase.getProjectMilestones(projectId);
        List<GetProjectMilestoneItemResponse> items = result.items().stream()
                .map(item -> new GetProjectMilestoneItemResponse(
                        item.id(),
                        item.projectId(),
                        item.name(),
                        item.descriptionPreview(),
                        item.status(),
                        item.startDate(),
                        item.endDate(),
                        item.progressPercent(),
                        item.totalCaseCount(),
                        item.completedCaseCount(),
                        item.testRunCount()))
                .toList();
        return ResponseEntity.ok(new GetProjectMilestoneResponse(items));
    }

     /**
     * 마일스톤 상세 정보와 통계를 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 상세 응답
     */
    @GetMapping("/milestones/{milestoneId}")
    public ResponseEntity<GetMilestoneDetailResponse> getMilestoneDetail(@PathVariable String milestoneId) {
        // TODO(authz): 인증/인가 도입 시 상위 projectId 경로 구조를 포함할지, milestone 소속 검증을 서비스에서 강제할지 정리할 것.
        GetMilestoneDetailResult result = queryMilestoneUseCase.getMilestoneDetail(milestoneId);
        return ResponseEntity.ok(new GetMilestoneDetailResponse(
                result.id(),
                result.projectId(),
                result.name(),
                result.description(),
                result.status(),
                result.startDate(),
                result.endDate(),
                result.progressPercent(),
                result.completedCaseCount(),
                result.totalCaseCount(),
                result.testRunCount(),
                result.createdAt(),
                result.updatedAt(),
                result.testCases().stream()
                        .map(item -> new GetMilestoneTestCaseItemResponse(
                                item.id(),
                                item.caseKey(),
                                item.name(),
                                item.latestResultStatus()))
                        .toList(),
                result.testSuites().stream()
                        .map(item -> new GetMilestoneTestSuiteItemResponse(
                                item.id(),
                                item.name(),
                                item.description(),
                                item.linkedTestCaseCount()))
                        .toList(),
                result.testRuns().stream()
                        .map(item -> new GetMilestoneTestRunItemResponse(
                                item.id(),
                                item.name(),
                                item.createdAt(),
                                item.status(),
                                item.progressPercent()))
                        .toList()));
    }

    
}
