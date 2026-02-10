package com.data_arts_studio.web_mvp_back.test_case.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.request.CreateTestCaseRequest;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.CreateTestCaseResponse;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.service.TestCaseResult;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestPriority;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/projects/{projectId}/test-cases")
@RequiredArgsConstructor
public class TestCaseController {
    private final CreateTestCaseUseCase createTestCaseUseCase;

    @PostMapping
    public ResponseEntity<CreateTestCaseResponse> createTestCase(@PathVariable("projectId") String projectId, @RequestBody CreateTestCaseRequest request) {
    CreateTestCaseCommand command = CreateTestCaseCommand.builder()
                    .projectId(projectId) 
                    .testSuiteId(request.testSuiteId())
                    .name(request.name())
                    .priority(TestPriority.MEDIUM) // 일단 기본 값 --- 나중에 요청에서 받도록 변경
                    .testType(request.testType())     
                    .tags(request.tags())             
                    .preCondition(request.preCondition()) 
                    .steps(request.steps())           
                    .expectedResult(request.expectedResult()) 
                    .build();
        TestCaseResult result = createTestCaseUseCase.createTestCase(command);
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
    
}
