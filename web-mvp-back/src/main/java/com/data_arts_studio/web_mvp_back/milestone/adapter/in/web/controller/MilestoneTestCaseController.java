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

import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request.AssignTestCaseToMilestoneRequest;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request.ReplaceMilestoneTestCasesRequest;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestCaseItemResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.GetMilestoneTestCasesResponse;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.AssignTestCaseToMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.RemoveTestCaseFromMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ReplaceMilestoneTestCasesCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.AssignTestCaseToMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestCaseLinksUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.RemoveTestCaseFromMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.ReplaceMilestoneTestCasesUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestCasesResult;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 테스트 케이스 링크 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MilestoneTestCaseController {
    private final AssignTestCaseToMilestoneUseCase assignTestCaseToMilestoneUseCase;
    private final ReplaceMilestoneTestCasesUseCase replaceMilestoneTestCasesUseCase;
    private final RemoveTestCaseFromMilestoneUseCase removeTestCaseFromMilestoneUseCase;
    private final QueryMilestoneTestCaseLinksUseCase queryMilestoneTestCaseLinksUseCase;

    /**
     * 마일스톤에 포함된 테스트 케이스 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 케이스 목록 응답
     */
    @GetMapping("/milestones/{milestoneId}/test-cases")
    public ResponseEntity<GetMilestoneTestCasesResponse> getMilestoneTestCases(@PathVariable String milestoneId) {
        GetMilestoneTestCasesResult result = queryMilestoneTestCaseLinksUseCase.getMilestoneTestCases(milestoneId);
        List<GetMilestoneTestCaseItemResponse> items = result.items().stream()
                .map(item -> new GetMilestoneTestCaseItemResponse(
                        item.id(),
                        item.caseKey(),
                        item.name(),
                        item.latestResultStatus()))
                .toList();
        return ResponseEntity.ok(new GetMilestoneTestCasesResponse(result.milestoneId(), items));
    }

    /**
     * 마일스톤에 테스트 케이스 한 건을 연결
     *
     * @param milestoneId 마일스톤 식별자
     * @param request 연결 요청 DTO
     * @return 생성 응답
     */
    @PostMapping("/milestones/{milestoneId}/test-cases")
    public ResponseEntity<Void> assignTestCaseToMilestone(@PathVariable String milestoneId,
                                                          @RequestBody AssignTestCaseToMilestoneRequest request) {
        assignTestCaseToMilestoneUseCase.assignTestCaseToMilestone(AssignTestCaseToMilestoneCommand.builder()
                .milestoneId(milestoneId)
                .testCaseId(request.testCaseId())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 마일스톤 테스트 케이스 범위를 전체 교체
     *
     * @param milestoneId 마일스톤 식별자
     * @param request 범위 교체 요청 DTO
     * @return no content 응답
     */
    @PutMapping("/milestones/{milestoneId}/test-cases")
    public ResponseEntity<Void> replaceMilestoneTestCases(@PathVariable String milestoneId,
                                                          @RequestBody ReplaceMilestoneTestCasesRequest request) {
        replaceMilestoneTestCasesUseCase.replaceMilestoneTestCases(ReplaceMilestoneTestCasesCommand.builder()
                .milestoneId(milestoneId)
                .testCaseIds(request.testCaseIds())
                .build());
        return ResponseEntity.noContent().build();
    }

    /**
     * 마일스톤에서 테스트 케이스 연결 한 건을 해제
     *
     * @param milestoneId 마일스톤 식별자
     * @param testCaseId 테스트 케이스 식별자
     * @return no content 응답
     */
    @DeleteMapping("/milestones/{milestoneId}/test-cases/{testCaseId}")
    public ResponseEntity<Void> removeTestCaseFromMilestone(@PathVariable String milestoneId,
                                                            @PathVariable String testCaseId) {
        removeTestCaseFromMilestoneUseCase.removeTestCaseFromMilestone(RemoveTestCaseFromMilestoneCommand.builder()
                .milestoneId(milestoneId)
                .testCaseId(testCaseId)
                .build());
        return ResponseEntity.noContent().build();
    }
}
