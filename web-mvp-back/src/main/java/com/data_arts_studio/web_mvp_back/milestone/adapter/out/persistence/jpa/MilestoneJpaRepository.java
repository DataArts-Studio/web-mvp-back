package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneJpaRepository extends JpaRepository<MilestoneJpaEntity, UUID> {
}
