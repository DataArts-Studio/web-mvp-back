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
        // 유효성 검사 
        testSuiteCreateValidator.validate(command);
        TestSuiteId testSuiteId = TestSuiteId.create(); // TestSuite id 생성
        // 도메인 객체 생성 후 저장
        TestSuite testSuite = new TestSuite(testSuiteId, new ProjectId(command.projectId()), command.name(), 0);
        saveTestSuitePort.save(testSuite);

        // 응답 DTO 반환 
        return new TestSuiteResult(testSuiteId.getId(), command.projectId(), command.name(), testSuite.getCreatedAt());
    }
}
