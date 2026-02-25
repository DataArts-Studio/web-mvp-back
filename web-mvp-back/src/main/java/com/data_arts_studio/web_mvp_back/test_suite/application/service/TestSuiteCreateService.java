package com.data_arts_studio.web_mvp_back.test_suite.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.CreateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.CreateTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.SaveTestSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.validator.TestSuiteCreateValidator;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuite;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TestSuiteCreateService implements CreateTestSuiteUseCase{
    private final TestSuiteCreateValidator testSuiteCreateValidator;
    private final SaveTestSuitePort saveTestSuitePort;
    @Override
    public TestSuiteResult createTestSuite(CreateTestSuiteCommand command) {
        testSuiteCreateValidator.validate(command);
        String normalizedName = command.name().trim(); // 데이터 앞뒤 공백 제거
        TestSuiteId testSuiteId = TestSuiteId.create(); 
        TestSuite testSuite = new TestSuite(testSuiteId, new ProjectId(command.projectId()), normalizedName,command.description(), 0);
        saveTestSuitePort.createTestSuite(testSuite);
        return new TestSuiteResult(testSuiteId.getId(), command.projectId(), testSuite.getName(), testSuite.getDescription(), testSuite.getCreatedAt());
    }
}
