package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

/**
 * 스위트-테스트케이스 연결을 단건 해제하기 위한 아웃 포트.
 *
 * 테스트케이스 자체를 삭제하지 않고, 스위트와의 링크만 제거
 */
public interface RemoveTestCaseFromSuitePort {

    /**
     * 특정 스위트와 테스트케이스 간 연결 1건을 제거
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    void remove(String projectId, String suiteId, String testCaseId);
}
