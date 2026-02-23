package com.data_arts_studio.web_mvp_back.test_case.application.port.in;

import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.TestCaseDetailRespose;
import com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response.TestCaseListItemResponse;
import java.util.List;

/**
 * 테스트 케이스 조회 유스케이스 인터페이스
 */
public interface GetTestCaseUseCase {
    // 특정 테스트 케이스 상세 조회
    TestCaseDetailRespose getTestCaseDetails(String projectId, String testCaseId);
    // 프로젝트 내 모든 테스트 케이스 조회
    List<TestCaseListItemResponse> getProjectTestCases(String projectId);
}
