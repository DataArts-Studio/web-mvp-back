package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSuiteJpaRepository extends JpaRepository<TestSuiteJpaEntity, String> {
    // 아카이브 되지 않은  스위트끼리만 이름 중복 체크
    boolean existsByProjectIdAndNameAndArchivedAtIsNull(String projectId, String name);
    // 수정 시 수정하고 있는 스위트를 제외한 이름 중복 체크
    boolean existsByProjectIdAndNameAndIdNotAndArchivedAtIsNull(String projectId, String name, String id);
}
