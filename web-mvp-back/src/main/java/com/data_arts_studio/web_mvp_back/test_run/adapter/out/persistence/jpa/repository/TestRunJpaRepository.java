package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.entity.TestRunJpaEntity;

public interface TestRunJpaRepository extends JpaRepository<TestRunJpaEntity, UUID> {
}
