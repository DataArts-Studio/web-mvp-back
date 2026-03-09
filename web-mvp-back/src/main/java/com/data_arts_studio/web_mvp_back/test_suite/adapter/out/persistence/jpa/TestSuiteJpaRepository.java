package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSuiteJpaRepository extends JpaRepository<TestSuiteJpaEntity, UUID> {

    /**
     * 동일 프로젝트 내 활성(미아카이브) 테스트 스위트 이름 중복 여부를 조회
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 스위트 이름
     * @return 중복이면 {@code true}, 아니면 {@code false}
     */
    boolean existsByProjectIdAndNameAndArchivedAtIsNull(UUID projectId, String name);

    /**
     * 수정 대상 스위트를 제외한 활성(미아카이브) 이름 중복 여부를 조회
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 스위트 이름
     * @param id 현재 수정 중인 테스트 스위트 식별자
     * @return 중복이면 {@code true}, 아니면 {@code false}
     */
    boolean existsByProjectIdAndNameAndIdNotAndArchivedAtIsNull(UUID projectId, String name, UUID id);

    /**
     * 프로젝트 범위에서 테스트 스위트 단건을 조회한다.
     *
     * @param id 테스트 스위트 식별자
     * @param projectId 프로젝트 식별자
     * @return 조회 결과가 있으면 엔티티, 없으면 Optional.empty()
     */
    Optional<TestSuiteJpaEntity> findByIdAndProjectId(UUID id, UUID projectId);

    /**
     * 프로젝트에 속한 테스트 스위트 목록을 정렬 순서 오름차순으로 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 정렬된 테스트 스위트 엔티티 목록
     */
    List<TestSuiteJpaEntity> findAllByProjectIdOrderBySortOrderAsc(UUID projectId);
}
