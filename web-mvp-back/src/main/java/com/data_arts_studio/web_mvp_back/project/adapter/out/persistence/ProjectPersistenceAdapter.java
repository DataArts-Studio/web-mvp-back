package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaEntity;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaRepository;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.mapper.ProjectMapper;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectNamePort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.LoadProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements SaveProjectPort,
                                                  CheckProjectNamePort, 
                                                  LoadProjectPort
                                                  
                                                  {


    private final ProjectJpaRepository projectJpaRepository;
    private final ProjectMapper projectMapper;

    @Override   
    public boolean isProjectNameDuplicated(String name) {
        return projectJpaRepository.existsByName(name);
    }

    @Override
    public void save(Project project) {
        ProjectJpaEntity entity = projectMapper.toJpaEntity(project);
        projectJpaRepository.save(entity);
    }

    @Override
    public Optional<Project> loadById(ProjectId projectId) {
        return projectJpaRepository.findById(projectId.getId())
                .map(projectMapper::toDomain);
    }

    
}
