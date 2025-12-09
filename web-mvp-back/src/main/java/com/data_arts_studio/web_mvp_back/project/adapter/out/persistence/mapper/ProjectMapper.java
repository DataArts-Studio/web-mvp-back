package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.mapper;

import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaEntity;
import com.data_arts_studio.web_mvp_back.project.domain.Project;

public class ProjectMapper {
    public static ProjectJpaEntity toJpaEntity(Project project) {
        return new ProjectJpaEntity(project.getId().getId().toString(), project.getName(), project.getPassword(), project.getDescription(), project.getOwnerName(), project.getCreatedAt(), project.getUpdatedAt(), project.getDeletedAt());
    }
}   
