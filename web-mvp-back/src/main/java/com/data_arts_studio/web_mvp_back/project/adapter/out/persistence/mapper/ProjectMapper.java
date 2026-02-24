package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaEntity;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;


@Component
public class ProjectMapper {

    // Domain -> JPA
    public ProjectJpaEntity toJpaEntity(Project project) {
        return ProjectJpaEntity.builder()
                .id(project.getId().getId().toString())
                .name(project.getName())
                .identifier(project.getIdentifier())
                .description(project.getDescription())
                .ownerName(project.getOwnerName())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .archivedAt(project.getArchivedAt())
                .build();
    }

    // JPA -> Domain
    public Project toDomain(ProjectJpaEntity entity) {
        return new Project(
            new ProjectId(entity.getId()),
            entity.getName(),
            entity.getIdentifier(),
            entity.getDescription(),
            entity.getOwnerName(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getArchivedAt()
        );
    }
}
