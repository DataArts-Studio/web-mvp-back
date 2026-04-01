package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.controller;

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

import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.AssignTestCaseToSuiteRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.ReplaceSuiteTestCasesRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetSuiteTestCaseLinksResponse;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.AssignTestCaseToSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.RemoveTestCaseFromSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.ReplaceSuiteTestCasesCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.AssignTestCaseToSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.QuerySuiteTestCaseLinksUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.RemoveTestCaseFromSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.ReplaceSuiteTestCasesUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetSuiteTestCaseLinksResult;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 스위트와 테스트 케이스 사이의 링크를 다루는 웹 어댑터
 */
@RestController
@RequestMapping("/api/projects/{projectId}/suites/{suiteId}/test-cases")
@RequiredArgsConstructor
public class TestSuiteTestCaseLinkController {
    private final AssignTestCaseToSuiteUseCase assignTestCaseToSuiteUseCase;
    private final RemoveTestCaseFromSuiteUseCase removeTestCaseFromSuiteUseCase;
    private final ReplaceSuiteTestCasesUseCase replaceSuiteTestCasesUseCase;
    private final QuerySuiteTestCaseLinksUseCase querySuiteTestCaseLinksUseCase;

    /**
     * 특정 테스트 스위트에 연결된 테스트 케이스 ID 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 연결된 테스트 케이스 목록 응답
     */
    @GetMapping
    public ResponseEntity<GetSuiteTestCaseLinksResponse> getSuiteTestCaseLinks(@PathVariable String projectId,
                                                                               @PathVariable String suiteId) {
        GetSuiteTestCaseLinksResult result = querySuiteTestCaseLinksUseCase.getSuiteTestCaseLinks(projectId, suiteId);
        return ResponseEntity.ok(new GetSuiteTestCaseLinksResponse(result.suiteId(), result.testCaseIds()));
    }

    /**
     * 테스트 스위트에 테스트 케이스 1건을 연결
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param request 연결할 테스트 케이스 식별자를 담은 요청 DTO
     * @return 생성 응답
     */
    @PostMapping
    public ResponseEntity<Void> assignTestCaseToSuite(@PathVariable String projectId,
                                                      @PathVariable String suiteId,
                                                      @RequestBody AssignTestCaseToSuiteRequest request) {
        AssignTestCaseToSuiteCommand command = AssignTestCaseToSuiteCommand.builder()
                .projectId(projectId)
                .suiteId(suiteId)
                .testCaseId(request.testCaseId())
                .build();
        assignTestCaseToSuiteUseCase.assignTestCaseToSuite(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 테스트 스위트에 연결된 테스트 케이스 구성을 전체 교체
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param request 최종 테스트 케이스 목록을 담은 요청 DTO
     * @return no content 응답
     */
    @PutMapping
    public ResponseEntity<Void> replaceSuiteTestCases(@PathVariable String projectId,
                                                      @PathVariable String suiteId,
                                                      @RequestBody ReplaceSuiteTestCasesRequest request) {
        ReplaceSuiteTestCasesCommand command = ReplaceSuiteTestCasesCommand.builder()
                .projectId(projectId)
                .suiteId(suiteId)
                .testCaseIds(request.testCaseIds())
                .build();
        replaceSuiteTestCasesUseCase.replaceSuiteTestCases(command);
        return ResponseEntity.noContent().build();
    }

    /**
     * 테스트 스위트에서 테스트 케이스 연결 1건을 해제
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseId 연결 해제할 테스트 케이스 식별자
     * @return no content 응답
     */
    @DeleteMapping("/{testCaseId}")
    public ResponseEntity<Void> removeTestCaseFromSuite(@PathVariable String projectId,
                                                        @PathVariable String suiteId,
                                                        @PathVariable String testCaseId) {
        RemoveTestCaseFromSuiteCommand command = RemoveTestCaseFromSuiteCommand.builder()
                .projectId(projectId)
                .suiteId(suiteId)
                .testCaseId(testCaseId)
                .build();
        removeTestCaseFromSuiteUseCase.removeTestCaseFromSuite(command);
        return ResponseEntity.noContent().build();
    }
}
