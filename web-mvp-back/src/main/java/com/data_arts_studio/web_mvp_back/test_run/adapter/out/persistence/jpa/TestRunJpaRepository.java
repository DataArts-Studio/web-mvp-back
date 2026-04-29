package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRunJpaRepository extends JpaRepository<TestRunJpaEntity, UUID> {

    /**
     * 같은 프로젝트에서 아카이브되지 않은 테스트 런 이름 존재 여부를 조회
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 런 이름
     * @return 이름 존재 여부
     */
    boolean existsByProjectIdAndNameAndArchivedAtIsNull(UUID projectId, String name);
}
