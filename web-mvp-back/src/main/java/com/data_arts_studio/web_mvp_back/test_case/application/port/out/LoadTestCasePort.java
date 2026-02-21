package com.data_arts_studio.web_mvp_back.test_case.application.port.out;

import java.util.List;
import java.util.Optional;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;

public interface LoadTestCasePort {
    /**
     * 식별자를 통해 테스트 케이스 도메인 엔티티를 로드
     * @param testCaseId 도메인 값 객체 식별자
     * @return 검색된 테스트 케이스 (없을 경우 Optional.empty())
     */
    Optional<TestCase> loadTestCase(TestCaseId testCaseId);

    /**
     * 프로젝트에 속한 테스트 케이스 목록 로드
     * @param projectId 프로젝트 식별자
     * @return 테스트 케이스 목록
     */
    List<TestCase> loadByProjectId(String projectId);
 
}
