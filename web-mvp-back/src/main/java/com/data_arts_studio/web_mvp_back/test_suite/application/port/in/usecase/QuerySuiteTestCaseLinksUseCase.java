package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetSuiteTestCaseLinksResult;

/**
 * 테스트 스위트에 연결된 테스트 케이스 링크 목록을 조회하는 유스케이스.
 */
public interface QuerySuiteTestCaseLinksUseCase {

    /**
     * 특정 스위트에 현재 연결된 테스트 케이스 ID 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 연결된 테스트 케이스 목록 조회 결과
     */
    GetSuiteTestCaseLinksResult getSuiteTestCaseLinks(String projectId, String suiteId);
}
