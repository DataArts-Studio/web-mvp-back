package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRunJpaRepository extends JpaRepository<TestCaseRunJpaEntity, UUID> {
}
