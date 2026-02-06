package com.data_arts_studio.web_mvp_back.test_case.application.port.out;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;

public interface SaveTestCasePort {
    /** 
     * 테스트 케이스 도메인 엔티티를 저장
     * @param testCase 테스트 케이스 도메인 엔티티
     */
    void save(TestCase testCase);

    /** 
     * 테스트 케이스 도메인 엔티티를 수정
     * @param testCase 테스트 케이스 도메인 엔티티
     */
    void updateTestCase(TestCase testCase);
}
