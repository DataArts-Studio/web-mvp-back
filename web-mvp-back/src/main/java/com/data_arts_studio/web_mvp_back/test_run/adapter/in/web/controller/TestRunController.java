package com.data_arts_studio.web_mvp_back.test_run.adapter.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.test_run.adapter.in.web.request.CreateTestRunRequest;
import com.data_arts_studio.web_mvp_back.test_run.adapter.in.web.response.CreateTestRunResponse;
import com.data_arts_studio.web_mvp_back.test_run.application.port.in.command.CreateTestRunCommand;
import com.data_arts_studio.web_mvp_back.test_run.application.port.in.usecase.CreateTestRunUseCase;
import com.data_arts_studio.web_mvp_back.test_run.application.service.result.CreateTestRunResult;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 런 생성 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestRunController {
    private final CreateTestRunUseCase createTestRunUseCase;

    /**
     * 하나 이상의 마일스톤을 기준으로 테스트 런을 생성
     *
     * @param request 테스트 런 생성 요청
     * @return 생성된 테스트 런 응답
     */
    @PostMapping("/test-runs")
    public ResponseEntity<CreateTestRunResponse> createTestRun(@RequestBody CreateTestRunRequest request) {
        CreateTestRunResult result = createTestRunUseCase.createTestRun(
                CreateTestRunCommand.builder()
                        .projectId(request.projectId())
                        .milestoneIds(request.milestoneIds())
                        .name(request.name())
                        .description(request.description())
                        .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateTestRunResponse(
                result.id(),
                result.projectId(),
                result.milestoneIds(),
                result.name(),
                result.description(),
                result.status(),
                result.createdAt(),
                result.updatedAt(),
                result.archivedAt(),
                result.lifecycleStatus(),
                result.shareToken(),
                result.shareExpiresAt()
                // ,result.shareAiSummary()
            ));
    }
}
