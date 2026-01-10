package com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.request.CreateTestSuiteRequest;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.in.web.response.CreateTestSuiteResponse;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.CreateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.CreateTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.TestSuiteResult;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/projects/{projectId}/suites")
@RequiredArgsConstructor
public class TestSuiteController {
    private final CreateTestSuiteUseCase createTestSuiteUseCase;

    @PostMapping
    public ResponseEntity<CreateTestSuiteResponse> createTestSuite(@PathVariable String projectId, @RequestBody CreateTestSuiteRequest request) {
        CreateTestSuiteCommand command = new CreateTestSuiteCommand(projectId, request.name());
        TestSuiteResult result = createTestSuiteUseCase.createTestSuite(command);
        CreateTestSuiteResponse response = new CreateTestSuiteResponse(
                result.id(),
                result.projectId(),
                result.name(),
                result.createdAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    
}
