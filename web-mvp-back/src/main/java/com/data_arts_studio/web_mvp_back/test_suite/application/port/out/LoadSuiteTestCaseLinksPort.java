package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

import java.util.List;

/**
 * 스위트-테스트케이스 조회를 위한 아웃 포트.
 *
 * 서비스 계층에서 프로젝트 일치 검증, 차집합 비교, 교체 연산 전후 확인 등에 활용
 */
public interface LoadSuiteTestCaseLinksPort {

    /**
     * 특정 스위트에 현재 연결된 테스트 케이스 ID 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 현재 스위트에 연결된 테스트 케이스 ID 목록
     */
    List<String> loadTestCaseIdsBySuite(String projectId, String suiteId);
}
