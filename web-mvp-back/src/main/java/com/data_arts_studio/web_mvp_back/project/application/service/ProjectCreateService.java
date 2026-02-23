package com.data_arts_studio.web_mvp_back.project.application.service;

import org.springframework.stereotype.Service;

import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectUseCase;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.validator.ProjectCreateValidator;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


/** 프로젝트 생성 서비스 구현체 */
@Service
@RequiredArgsConstructor
public class ProjectCreateService implements CreateProjectUseCase {
    private final SaveProjectPort saveProjectPort;
    private final PasswordEncoder passwordEncoder;
    private final ProjectCreateValidator projectCreateValidator;


    /** 프로젝트 생성
    * @param CreateProjectCommand
    * @return ProjectResult             
    */
    @Override
    public ProjectResult createProject(CreateProjectCommand command) {
        projectCreateValidator.validate(command);
        ProjectId projectId = ProjectId.create();       
        String hashedPassword = passwordEncoder.encode(command.identifier());
        Project project = new Project(projectId, command.name() ,hashedPassword, command.description(),command.ownerName());
        saveProjectPort.save(project);
        return new ProjectResult(projectId.getId(), project.getName(), project.getDescription(), project.getOwnerName(), project.getCreatedAt());
    }
}
