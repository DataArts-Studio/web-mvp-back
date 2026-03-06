package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

/**
 * 스위트-테스트케이스 연결을 단건 추가하기 위한 아웃 포트.
 *
 * 이 포트는 링크 저장(insert) 책임만 가지며, 존재 여부나 프로젝트 일치 검증은 서비스 계층에서 선행함
 * 
 */
public interface AssignTestCaseToSuitePort {

    /**
     * 특정 프로젝트의 스위트와 테스트케이스를 1건 연결한다.
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    void assign(String projectId, String suiteId, String testCaseId);
}
