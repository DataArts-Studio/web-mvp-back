package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSuiteJpaRepository extends JpaRepository<TestSuiteJpaEntity, String> {
    // 프로젝트 ID와 이름으로 삭제되지 않은 테스트스위트 존재 여부 확인
    boolean existsByProjectIdAndNameAndDeletedAtIsNull(String projectId, String name);
}
