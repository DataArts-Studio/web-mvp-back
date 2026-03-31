package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request.AssignTestSuiteToMilestoneRequest;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request.ReplaceMilestoneTestSuitesRequest;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestSuiteItemResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestSuitesResponse;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.AssignTestSuiteToMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.RemoveTestSuiteFromMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ReplaceMilestoneTestSuitesCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.AssignTestSuiteToMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestSuiteLinksUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.RemoveTestSuiteFromMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.ReplaceMilestoneTestSuitesUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestSuitesResult;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 테스트 스위트 링크 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MilestoneTestSuiteController {
    private final AssignTestSuiteToMilestoneUseCase assignTestSuiteToMilestoneUseCase;
    private final ReplaceMilestoneTestSuitesUseCase replaceMilestoneTestSuitesUseCase;
    private final RemoveTestSuiteFromMilestoneUseCase removeTestSuiteFromMilestoneUseCase;
    private final QueryMilestoneTestSuiteLinksUseCase queryMilestoneTestSuiteLinksUseCase;

    /**
     * 마일스톤에 연결된 테스트 스위트 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 목록 응답
     */
    @GetMapping("/milestones/{milestoneId}/test-suites")
    public ResponseEntity<GetMilestoneTestSuitesResponse> getMilestoneTestSuites(@PathVariable String milestoneId) {
        GetMilestoneTestSuitesResult result = queryMilestoneTestSuiteLinksUseCase.getMilestoneTestSuites(milestoneId);
        List<GetMilestoneTestSuiteItemResponse> items = result.items().stream()
                .map(item -> new GetMilestoneTestSuiteItemResponse(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.linkedTestCaseCount()))
                .toList();
        return ResponseEntity.ok(new GetMilestoneTestSuitesResponse(result.milestoneId(), items));
    }

    /**
     * 마일스톤에 테스트 스위트 한 건을 연결
     *
     * @param milestoneId 마일스톤 식별자
     * @param request 연결 요청 DTO
     * @return 생성 응답
     */
    @PostMapping("/milestones/{milestoneId}/test-suites")
    public ResponseEntity<Void> assignTestSuiteToMilestone(@PathVariable String milestoneId,
                                                           @RequestBody AssignTestSuiteToMilestoneRequest request) {
        assignTestSuiteToMilestoneUseCase.assignTestSuiteToMilestone(AssignTestSuiteToMilestoneCommand.builder()
                .milestoneId(milestoneId)
                .testSuiteId(request.testSuiteId())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 마일스톤 테스트 스위트 범위를 전체 교체
     *
     * @param milestoneId 마일스톤 식별자
     * @param request 범위 교체 요청 DTO
     * @return no content 응답
     */
    @PutMapping("/milestones/{milestoneId}/test-suites")
    public ResponseEntity<Void> replaceMilestoneTestSuites(@PathVariable String milestoneId,
                                                           @RequestBody ReplaceMilestoneTestSuitesRequest request) {
        replaceMilestoneTestSuitesUseCase.replaceMilestoneTestSuites(ReplaceMilestoneTestSuitesCommand.builder()
                .milestoneId(milestoneId)
                .testSuiteIds(request.testSuiteIds())
                .build());
        return ResponseEntity.noContent().build();
    }

    /**
     * 마일스톤에서 테스트 스위트 연결 한 건을 해제
     *
     * @param milestoneId 마일스톤 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return no content 응답
     */
    @DeleteMapping("/milestones/{milestoneId}/test-suites/{suiteId}")
    public ResponseEntity<Void> removeTestSuiteFromMilestone(@PathVariable String milestoneId,
                                                             @PathVariable String suiteId) {
        removeTestSuiteFromMilestoneUseCase.removeTestSuiteFromMilestone(RemoveTestSuiteFromMilestoneCommand.builder()
                .milestoneId(milestoneId)
                .testSuiteId(suiteId)
                .build());
        return ResponseEntity.noContent().build();
    }
}
