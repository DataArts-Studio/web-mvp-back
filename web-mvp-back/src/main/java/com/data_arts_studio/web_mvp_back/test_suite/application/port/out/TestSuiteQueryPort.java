package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

import java.util.List;
import java.util.Optional;

import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetProjectTestSuiteItemResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetTestSuiteDetailResult;

/**
 * 테스트 스위트 조회 전용 아웃바운드 포트.
 *
 * 애플리케이션 서비스가 영속 계층의 구현 세부사항에 의존하지 않고
 * 테스트 스위트 목록/상세 조회 기능을 수행할 수 있도록 조회 계약을 정의
 */
public interface TestSuiteQueryPort {

    /**
     * 특정 프로젝트에 속한 테스트 스위트 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 프로젝트 내 테스트 스위트 목록
     */
    List<GetProjectTestSuiteItemResult> findAllByProject(String projectId);

    /**
     * 특정 프로젝트 범위에서 테스트 스위트 단건 상세 정보를 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 조회 결과가 있으면 상세 정보, 없으면 Optional.empty()
     */
    Optional<GetTestSuiteDetailResult> findDetail(String projectId, String suiteId);

}
