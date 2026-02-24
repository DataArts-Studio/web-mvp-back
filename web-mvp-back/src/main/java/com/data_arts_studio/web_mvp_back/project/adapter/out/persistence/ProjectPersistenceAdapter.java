package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaEntity;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaRepository;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.mapper.ProjectMapper;
import com.data_arts_studio.web_mvp_back.project.application.port.out.ArchiveProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectNamePort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.LoadProjectPort;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

import lombok.RequiredArgsConstructor;

/**
 * Project 서비스가 사용하는 포트 구현체
 */
@Repository
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements LoadProjectPort, 
                                                SaveProjectPort,
                                                ArchiveProjectPort,
                                                CheckProjectNamePort 
                                                  {


    private final ProjectJpaRepository projectJpaRepository;
    private final ProjectMapper projectMapper;

    // LoadProjectPort 구현
    @Override
    public Optional<Project> loadById(ProjectId projectId) {
        return projectJpaRepository.findById(projectId.getId())
                .map(projectMapper::toDomain);
    }
    /**
     * 프로젝트 저장
      * @param project 저장할 프로젝트 도메인 객체
     */
    @Override
    public void createProject(Project project) {
        ProjectJpaEntity entity = projectMapper.toJpaEntity(project);
        projectJpaRepository.save(entity);
    }
    /**
     * 프로젝트 업데이트
      * @param project 저장할 프로젝트 도메인 객체
     */
    @Override
    public void updateProject(Project project) {
        ProjectJpaEntity entity = projectMapper.toJpaEntity(project);
        projectJpaRepository.save(entity);
    }

    // CheckProjectNamePort 구현
    @Override   
    public boolean isProjectNameDuplicated(String name) {
        return projectJpaRepository.existsByName(name);
    }

    // ArchiveProjectPort 구현 
    @Override
    public void archive(Project project) {
        projectJpaRepository.save(projectMapper.toJpaEntity(project));  
    }



    
}
