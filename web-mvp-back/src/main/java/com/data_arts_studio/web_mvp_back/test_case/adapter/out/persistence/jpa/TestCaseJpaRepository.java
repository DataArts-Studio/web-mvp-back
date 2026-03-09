package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseJpaRepository extends JpaRepository<TestCaseJpaEntity, UUID> {
    List<TestCaseJpaEntity> findAllByProjectId(UUID projectId);
    List<TestCaseJpaEntity> findAllByProjectIdOrderBySortOrderAscCreatedAtAsc(UUID projectId);
}
