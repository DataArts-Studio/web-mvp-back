package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectPreferenceJpaRepository extends JpaRepository<ProjectPreferenceJpaEntity, UUID> {
}
