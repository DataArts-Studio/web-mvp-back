package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSuiteJpaRepository extends JpaRepository<TestSuiteJpaEntity, String> {
    // 아카이브 되지 않은  스위트끼리만 이름 중복 체크
    boolean existsByProjectIdAndNameAndArchivedAtIsNull(String projectId, String name);
}
