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
    * @param
    * @return ProjectResult
    */
    @Override
    public ProjectResult createProject(CreateProjectCommand command) {
        // 유효성 검사
        projectCreateValidator.validate(command);
        // 프로젝트 id 생성, 비밀번호 해싱
        ProjectId projectId = ProjectId.create();
        String hashedPassword = passwordEncoder.encode(command.password());

        // 도메인 객체 생성 후 저장
        Project project = new Project(projectId, command.name(), hashedPassword, command.description(),command.ownerName());
        saveProjectPort.save(project);
    

        // 응답 DTO 반환
        return new ProjectResult(
            projectId.getId(),
            command.name(),
            command.description(),
            command.ownerName()
        );

    }
}
