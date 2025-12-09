package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaEntity;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaRepository;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.mapper.ProjectMapper;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectNamePort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.domain.Project;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements SaveProjectPort, CheckProjectNamePort{
    private final ProjectJpaRepository projectJpaRepository;

    @Override   
    public boolean isProjectNameDuplicated(String name) {
        return projectJpaRepository.existsByName(name);
    }

    @Override
    public void save(Project project) {
        ProjectJpaEntity entity = ProjectMapper.toJpaEntity(project);
        projectJpaRepository.save(entity);
    }
    
}
