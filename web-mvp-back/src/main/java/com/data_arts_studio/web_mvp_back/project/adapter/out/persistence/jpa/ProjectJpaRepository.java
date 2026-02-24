package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, String> {
    // 프로젝트 이름으로 존재 여부 확인
    boolean existsByName(String name);
    // 아카이브 되지 않은 프로젝트 ID로 존재 여부 확인
    boolean existsByArchivedAtIsNullAndId(String projectId);


}
