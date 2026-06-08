package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestRunItemResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestRunsResponse;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestRunsUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestRunsResult;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 상세 화면의 테스트 실행 이력 조회 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MilestoneTestRunController {
    private final QueryMilestoneTestRunsUseCase queryMilestoneTestRunsUseCase;

    /**
     * 마일스톤 기반 테스트 실행 이력을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 실행 이력 응답
     */
    @GetMapping("/milestones/{milestoneId}/test-runs")
    public ResponseEntity<GetMilestoneTestRunsResponse> getMilestoneTestRuns(@PathVariable String milestoneId) {
        GetMilestoneTestRunsResult result = queryMilestoneTestRunsUseCase.getMilestoneTestRuns(milestoneId);
        List<GetMilestoneTestRunItemResponse> items = result.items().stream()
                .map(item -> new GetMilestoneTestRunItemResponse(
                        item.id(),
                        item.name(),
                        item.createdAt(),
                        item.status(),
                        item.progressPercent()))
                .toList();
        return ResponseEntity.ok(new GetMilestoneTestRunsResponse(result.milestoneId(), items));
    }
}
