package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;

/**
 * 테스트 실행에 포함될 테스트 케이스 실행 레코드 저장 포트
 */
public interface SaveTestCaseRunsPort {

    /**
     * 테스트 실행에 포함된 테스트 케이스 실행 레코드를 일괄 생성
     *
     * @param testRunId 테스트 실행 식별자
     * @param testCaseIds 포함할 테스트 케이스 식별자 목록
     */
    void createTestCaseRuns(String testRunId, List<String> testCaseIds);
}
