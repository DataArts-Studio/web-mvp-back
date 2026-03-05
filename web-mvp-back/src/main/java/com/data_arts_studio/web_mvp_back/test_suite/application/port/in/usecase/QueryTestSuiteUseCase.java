package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase;


import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetProjectTestSuiteResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetTestSuiteDetailResult;

/**
 * 테스트 스위트 조회 전용 인바운드 유스케이스.
 *
 * 웹 어댑터 등 외부 입력 계층이 테스트 스위트 목록/상세 조회를 수행할 때
 * 애플리케이션 서비스에 전달하는 조회 계약을 정의
 */
public interface QueryTestSuiteUseCase {

    /**
     * 특정 프로젝트에 속한 테스트 스위트 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 프로젝트 테스트 스위트 목록 조회 결과
     */
    GetProjectTestSuiteResult getProjectTestSuites(String projectId);

    /**
     * 특정 프로젝트 범위에서 테스트 스위트 단건 상세 정보를 조회
     *
     * @param projectId 프로젝트 식별자
     * @param testSuiteId 테스트 스위트 식별자
     * @return 테스트 스위트 상세 조회 결과
     */
    GetTestSuiteDetailResult getTestSuiteDetail(String projectId, String testSuiteId);
}
