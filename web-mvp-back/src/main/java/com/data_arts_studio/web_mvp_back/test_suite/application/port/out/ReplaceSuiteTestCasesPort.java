package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

import java.util.List;

/**
 * 스위트의 테스트케이스 구성을 전체 교체하기 위한 아웃 포트.
 *
 * 일반적으로 기존 링크를 제거한 뒤 새 링크를 bulk insert하며, 구현체는 트랜잭션 내에서 원자적으로 처리해야 함
 */
public interface ReplaceSuiteTestCasesPort {

    /**
     * 특정 스위트에 연결된 테스트케이스 집합을 전달받은 목록으로 교체
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseIds 교체할 테스트 케이스 식별자 목록
     */
    void replace(String projectId, String suiteId, List<String> testCaseIds);
}
