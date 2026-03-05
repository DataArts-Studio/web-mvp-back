package com.data_arts_studio.web_mvp_back.test_suite.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.QueryTestSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.TestSuiteQueryPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetProjectTestSuiteItemResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetProjectTestSuiteResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetTestSuiteDetailResult;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TestSuiteQueryService implements QueryTestSuiteUseCase{
    private final TestSuiteQueryPort testSuiteQueryPort;

    @Override
    public GetProjectTestSuiteResult getProjectTestSuites(String projectId) {
        List<GetProjectTestSuiteItemResult>items = testSuiteQueryPort.findAllByProject(projectId);
        return GetProjectTestSuiteResult.builder().items(items).build();
    }

    @Override
    public GetTestSuiteDetailResult getTestSuiteDetail(String projectId, String testSuiteId) {
        return testSuiteQueryPort.findDetail(projectId, testSuiteId).orElseThrow(() -> new IllegalArgumentException("TestSuite를 찾을 수 없습니다."));
    }
    
}
