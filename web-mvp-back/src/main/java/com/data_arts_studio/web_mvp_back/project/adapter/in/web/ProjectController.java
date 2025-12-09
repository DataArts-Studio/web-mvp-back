package com.data_arts_studio.web_mvp_back.project.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.project.adapter.in.web.request.CreateProjectRequest;
import com.data_arts_studio.web_mvp_back.project.adapter.in.web.response.ProjectResponse;
import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final CreateProjectUseCase createProjectUseCase;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody CreateProjectRequest request) {
        // 요청을 커맨드 객체로 변환
        CreateProjectCommand command = new CreateProjectCommand(

            request.name(),
            request.password(),
            request.passwordConfirm(),
            request.description(),
            request.ownerName()
        );
        // 서비스 호출
        var result = createProjectUseCase.createProject(command);
        
        // 결과를 응답 객체로 변환
        ProjectResponse response = new ProjectResponse(
            result.getProjectId(),
            result.getName(),
            result.getDescription(),
            result.getOwnerName()
        );

        return ResponseEntity.ok(response);
    }
    
}
