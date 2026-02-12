package com.data_arts_studio.web_mvp_back.test_case.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.request.CreateTestCaseRequest;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.request.UpdateTestCaseRequest;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.CreateTestCaseResponse;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.TestCaseDetailRespose;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.UpdateTestCaseResponse;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.GetTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.UpdateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.UpdateTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.service.CreateTestCaseResult;
import com.data_arts_studio.web_mvp_back.test_case.application.service.UpdateTestCaseResult;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestPriority;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/projects/{projectId}/test-cases")
@RequiredArgsConstructor
public class TestCaseController {
    private final CreateTestCaseUseCase createTestCaseUseCase;
    private final UpdateTestCaseUseCase updateTestCaseUseCase;
    private final GetTestCaseUseCase getTestCaseUseCase;

    @PostMapping
    public ResponseEntity<CreateTestCaseResponse> createTestCase(@PathVariable("projectId") String projectId, @RequestBody CreateTestCaseRequest request) {
    String normalizedTestSuiteId = normalizeOptionalId(request.testSuiteId());
    CreateTestCaseCommand command = CreateTestCaseCommand.builder()
                    .projectId(projectId) 
                    .testSuiteId(normalizedTestSuiteId)
                    .name(request.name())
                    .priority(TestPriority.MEDIUM) // 일단 기본 값 --- 나중에 요청에서 받도록 변경
                    .testType(request.testType())     
                    .tags(request.tags())             
                    .preCondition(request.preCondition()) 
                    .steps(request.steps())           
                    .expectedResult(request.expectedResult()) 
                    .build();
        CreateTestCaseResult result = createTestCaseUseCase.createTestCase(command);
        CreateTestCaseResponse response = CreateTestCaseResponse.builder()
                .id(result.id())
                .caseKey(result.caseKey())
                .projectId(result.projectId())

                .testSuiteId(result.testSuiteId())
                .testSuiteName(result.testSuiteName())
                .name(result.name())
                .testType(result.testType())
                .resultStatus(result.status().name())
                .tags(result.tags())
                .preCondition(result.preCondition())
                .steps(result.steps())
                .expectedResult(result.expectedResult())
                .createdAt(java.time.OffsetDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 테스트 케이스 단일 상세 조회
    @GetMapping("/{testCaseId}")
    public ResponseEntity<TestCaseDetailRespose> getTestCase(@PathVariable("projectId") String projectId, @PathVariable("testCaseId") String testCaseId) {
        TestCaseDetailRespose response = getTestCaseUseCase.getTestCaseDetails(testCaseId);
        return ResponseEntity.ok(response);
    }
    



    @PutMapping("{testCaseId}")
    public ResponseEntity<UpdateTestCaseResponse> updateTestCase(@PathVariable("projectId") String projectId, 
                                                                 @PathVariable("testCaseId") String testCaseId,
                                                                 @RequestBody UpdateTestCaseRequest request) {
        // 테스트 스위트 아이디 정규화 (빈 값일 경우 null로 변환)
        String normalizedTestSuiteId = normalizeOptionalId(request.testSuiteId());
        UpdateTestCaseCommand command = UpdateTestCaseCommand.builder()
                        .testCaseId(testCaseId)
                        .testSuiteId(normalizedTestSuiteId)
                        .name(request.name())
                        .priority(TestPriority.MEDIUM) // 일단 기본 값 --- 나중에 요청에서 받도록 변경
                        .testType(request.testType())     
                        .tags(request.tags())             
                        .preCondition(request.preCondition()) 
                        .steps(request.steps())           
                        .expectedResult(request.expectedResult()) 
                        .build();           
        UpdateTestCaseResult result = updateTestCaseUseCase.updateTestCase(command);
        UpdateTestCaseResponse response = UpdateTestCaseResponse.builder()
                .id(result.id())
                .caseKey(result.caseKey())
                .projectId(result.projectId())

                .testSuiteId(result.testSuiteId())
                .testSuiteName(result.testSuiteName())
                .name(result.name())
                .testType(result.testType())
                .resultStatus(result.status().name())
                .tags(result.tags())
                .preCondition(result.preCondition())
                .steps(result.steps())
                .expectedResult(result.expectedResult())
                .updatedAt(result.updatedAt())
                .build();   
                        

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

      // Id 정규화 (빈 값일 경우 null로 변환 - 최상위 루트(Project) 밑으로 이동)
    private String normalizeOptionalId(String rawId) {
        if (rawId == null || rawId.isBlank()) {
            return null;
        }
        return rawId;
    }
    
}
