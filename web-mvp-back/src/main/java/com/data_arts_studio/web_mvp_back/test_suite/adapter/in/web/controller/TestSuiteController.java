package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.CreateTestSuiteRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.UpdateTestSuiteRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.CreateTestSuiteResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetProjectTestSuiteItemResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetProjectTestSuiteResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetTestSuiteDetailResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.UpdateTestSuiteResponse;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.CreateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.UpdateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.CreateTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.QueryTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.UpdateTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.CreateTestSuiteResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetProjectTestSuiteResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetTestSuiteDetailResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.UpdateTestSuiteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/projects/{projectId}/suites")
@RequiredArgsConstructor
public class TestSuiteController {
    private final CreateTestSuiteUseCase createTestSuiteUseCase;
    private final UpdateTestSuiteUseCase updateTestSuiteUseCase;
    private final QueryTestSuiteUseCase queryTestSuiteUseCase;

    /**
     * 특정 프로젝트에 테스트 스위트를 생성
     *
     * @param projectId 프로젝트 식별자
     * @param request 생성할 테스트 스위트 정보를 담은 요청 DTO
     * @return 생성된 테스트 스위트 응답
     */
    @PostMapping
    public ResponseEntity<CreateTestSuiteResponse> createTestSuite(@PathVariable String projectId, @RequestBody CreateTestSuiteRequest request) {
        CreateTestSuiteCommand command = CreateTestSuiteCommand.builder()
        .projectId(projectId)
        .name(request.name())
        .description(request.description())
        .build();
        CreateTestSuiteResult result = createTestSuiteUseCase.createTestSuite(command);
        CreateTestSuiteResponse response = new CreateTestSuiteResponse(
                result.id(),
                result.projectId(),
                result.name(),
                result.description(),
                result.createdAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 특정 프로젝트에 속한 테스트 스위트 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 프로젝트 테스트 스위트 목록 응답
     */
    @GetMapping
    public ResponseEntity<GetProjectTestSuiteResponse> getProjectTestSuites(@PathVariable String projectId) {
        
        GetProjectTestSuiteResult result = queryTestSuiteUseCase.getProjectTestSuites(projectId);

        List<GetProjectTestSuiteItemResponse> items = result.items().stream()
                .map(item -> new GetProjectTestSuiteItemResponse(
                        item.suiteId(),
                        item.name(),
                        item.type(),
                        item.testCaseCount(),
                        item.milestoneName(),
                        item.lastExecutedAt(),
                        item.executionCount()))
                .toList();

        return ResponseEntity.ok(new GetProjectTestSuiteResponse(items));
    }

    /**
     * 특정 프로젝트 범위에서 테스트 스위트 상세 정보를 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 테스트 스위트 상세 응답
     */
    @GetMapping("/{suiteId}")
    public ResponseEntity<GetTestSuiteDetailResponse> getTestSuiteDetail(@PathVariable String projectId,
                                                                         @PathVariable String suiteId) {
      
        GetTestSuiteDetailResult result = queryTestSuiteUseCase.getTestSuiteDetail(projectId, suiteId);
        GetTestSuiteDetailResponse response = new GetTestSuiteDetailResponse(
                result.suiteId(),
                result.name(),
                result.description(),
                result.createdAt(),
                result.testCaseCount(),
                result.executionCount(),
                result.lastPassRate());

        return ResponseEntity.ok(response);
    }

    /**
     * 특정 테스트 스위트의 이름과 설명을 수정
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param request 수정할 테스트 스위트 정보를 담은 요청 DTO
     * @return 수정된 테스트 스위트 응답
     */
    @PutMapping("/{suiteId}")
    public ResponseEntity<UpdateTestSuiteResponse> updateTestSuite(@PathVariable String projectId,
                                                                   @PathVariable String suiteId,
                                                                   @RequestBody UpdateTestSuiteRequest request) {
        UpdateTestSuiteCommand command = UpdateTestSuiteCommand.builder()
                .id(suiteId)
                .projectId(projectId)
                .name(request.name())
                .description(request.description())
                .build();
        UpdateTestSuiteResult result = updateTestSuiteUseCase.updateTestSuite(command);
        UpdateTestSuiteResponse response = new UpdateTestSuiteResponse(
                result.id(),
                result.projectId(),
                result.name(),
                result.description()
        );
        return ResponseEntity.ok(response);
    }
}
