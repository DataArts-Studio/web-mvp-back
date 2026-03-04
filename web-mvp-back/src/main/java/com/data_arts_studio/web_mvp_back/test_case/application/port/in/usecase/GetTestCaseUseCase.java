package com.data_arts_studio.web_mvp_back.test_case.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_case.application.service.result.GetTestCaseDetailResult;
import com.data_arts_studio.web_mvp_back.test_case.application.service.result.GetTestCaseListItemResult;
import java.util.List;

/**
 * 테스트 케이스 조회 유스케이스 인터페이스
 */
public interface GetTestCaseUseCase {
    // 특정 테스트 케이스 상세 조회
    GetTestCaseDetailResult getTestCaseDetails(String projectId, String testCaseId);
    // 프로젝트 내 모든 테스트 케이스 조회
    List<GetTestCaseListItemResult> getProjectTestCases(String projectId);
}
