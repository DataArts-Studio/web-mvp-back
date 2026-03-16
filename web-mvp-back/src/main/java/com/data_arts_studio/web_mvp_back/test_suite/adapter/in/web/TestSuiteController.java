package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.CreateTestSuiteRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.AssignTestCaseToSuiteRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.ReplaceSuiteTestCasesRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.UpdateTestSuiteRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.CreateTestSuiteResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetProjectTestSuiteItemResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetProjectTestSuiteResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetSuiteTestCaseLinksResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.GetTestSuiteDetailResponse;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.UpdateTestSuiteResponse;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.AssignTestCaseToSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.CreateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.RemoveTestCaseFromSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.ReplaceSuiteTestCasesCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.UpdateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.AssignTestCaseToSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.CreateTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.QuerySuiteTestCaseLinksUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.QueryTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.RemoveTestCaseFromSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.ReplaceSuiteTestCasesUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.UpdateTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.CreateTestSuiteResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetProjectTestSuiteResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetSuiteTestCaseLinksResult;
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
    private final AssignTestCaseToSuiteUseCase assignTestCaseToSuiteUseCase;
    private final RemoveTestCaseFromSuiteUseCase removeTestCaseFromSuiteUseCase;
    private final ReplaceSuiteTestCasesUseCase replaceSuiteTestCasesUseCase;
    private final QuerySuiteTestCaseLinksUseCase querySuiteTestCaseLinksUseCase;

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
    /**
     * 특정 테스트 스위트에 연결된 테스트 케이스 ID 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 연결된 테스트 케이스 목록 응답
     */
    @GetMapping("/{suiteId}/test-cases")
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
    @PostMapping("/{suiteId}/test-cases")
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
    @PutMapping("/{suiteId}/test-cases")
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
    @DeleteMapping("/{suiteId}/test-cases/{testCaseId}")
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
