package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, String> {
    boolean existsByName(String name);
}
