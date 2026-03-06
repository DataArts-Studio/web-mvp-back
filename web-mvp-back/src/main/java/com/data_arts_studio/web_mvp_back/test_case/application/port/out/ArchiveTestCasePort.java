package com.data_arts_studio.web_mvp_back.test_case.application.port.out;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;

/**
 * 테스트 케이스 아카이브 처리 아웃바운드 포트.
 *
 * 도메인에서 아카이브 상태로 변경된 테스트 케이스를 영속 계층에 반영하기 위한 저장 계약을 정의
 */
public interface ArchiveTestCasePort {

    /**
     * 테스트 케이스의 아카이브 상태를 저장
     *
     * @param testCase 아카이브 처리된 테스트 케이스 도메인 객체
     */
    void archive(TestCase testCase);
}
