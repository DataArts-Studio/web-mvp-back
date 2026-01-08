package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaEntity;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;


@Component
public class ProjectMapper {
    // Domain Entity -> JPA Entity
    public static ProjectJpaEntity toJpaEntity(Project project) {
        return new ProjectJpaEntity(
            project.getId().getId().toString(), 
            project.getName(), 
            project.getPassword(), 
            project.getDescription(), 
            project.getOwnerName(), 
            project.getStatus(),
            project.getCreatedAt(), 
            project.getUpdatedAt(), 
            project.getDeletedAt());
    }
    // JPA Entity -> Domain Entity
    public static Project toDomain(ProjectJpaEntity entity) {
        return new Project(
            new ProjectId(entity.getId()),
            entity.getName(),
            entity.getPassword(),
            entity.getDescription(),
            entity.getOwnerName(),
            entity.getStatus(), 
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }
}   
