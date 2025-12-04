package com.data_arts_studio.web_mvp_back.project.application.service;

import org.springframework.stereotype.Service;

import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectUseCase;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectNamePort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;


/** 프로젝트 생성 서비스 구현체 */
@Service
public class ProjectCreateService implements CreateProjectUseCase{
    private final CheckProjectNamePort checkProjectNamePort;
    private final SaveProjectPort saveProjectPort;

    public ProjectCreateService(CheckProjectNamePort checkProjectNamePort, SaveProjectPort saveProjectPort) {
        this.checkProjectNamePort = checkProjectNamePort;
        this.saveProjectPort = saveProjectPort;
    }

    /** 프로젝트 생성
    * @param
    * @return ProjectResult
    */
    @Override
    public ProjectResult createProject(CreateProjectCommand command) {
        // 이름 중복 체크 
        if (checkProjectNamePort.isProjectNameDuplicated(command.name())) {
            throw new IllegalArgumentException("이미 존재하는 프로젝트 이름입니다: " + command.name());
        }

        ProjectId projectId = ProjectId.create();
        // 도메인 객체 생성
        Project project = new Project(
            projectId,
            command.name(),
            command.password(),
            command.description(),
            command.ownerName()
        );
        // 저장
        saveProjectPort.save(project);
    

        // 응답 DTO 반환
        return new ProjectResult(
            projectId.getId(),
            command.name(),
            command.password(),
            command.description(),
            command.ownerName()
        );

    }
}
