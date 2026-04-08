package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.entity.SuiteTestCaseJpaEntity;

/**
 * 스위트-테스트케이스 조인 테이블(suite_test_cases) 전용 JPA 리포지토리
 */
public interface SuiteTestCaseJpaRepository extends JpaRepository<SuiteTestCaseJpaEntity, UUID> {
    /**
     * 특정 스위트-테스트케이스 링크의 존재 여부를 조회
     *
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseId 테스트 케이스 식별자
     * @return 링크 존재 여부
     */
    boolean existsBySuiteIdAndTestCaseId(UUID suiteId, UUID testCaseId);

    /**
     * 특정 스위트에 연결된 링크 전체를 조회
     *
     * @param suiteId 테스트 스위트 식별자
     * @return 링크 엔티티 목록
     */
    List<SuiteTestCaseJpaEntity> findAllBySuiteId(UUID suiteId);

    /**
     * 특정 스위트에 연결된 링크 전체를 삭제
     *
     * @param suiteId 테스트 스위트 식별자
     */
    void deleteBySuiteId(UUID suiteId);

    /**
     * 특정 스위트-테스트케이스 링크 1건을 삭제
     *
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    void deleteBySuiteIdAndTestCaseId(UUID suiteId, UUID testCaseId);
}
