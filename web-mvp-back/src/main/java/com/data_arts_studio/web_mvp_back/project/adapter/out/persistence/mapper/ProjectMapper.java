package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaEntity;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectSlug;


@Component
public class ProjectMapper {

    // Domain -> JPA
    public ProjectJpaEntity toJpaEntity(Project project) {
        return new ProjectJpaEntity(
            project.getId().getId().toString(),
            project.getName(),
            project.getSlug().getValue(),      
            project.getIdentifier(),           
            project.getDescription(),
            project.getOwnerName(),
            project.getCreatedAt(),
            project.getUpdatedAt(),
            project.getArchivedAt()
        );
    }

    // JPA -> Domain
    public Project toDomain(ProjectJpaEntity entity) {
        return new Project(
            new ProjectId(entity.getId()),
            entity.getName(),
            ProjectSlug.from(entity.getSlug()),   
            entity.getIdentifier(),
            entity.getDescription(),
            entity.getOwnerName(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getArchivedAt()
        );
    }
}
