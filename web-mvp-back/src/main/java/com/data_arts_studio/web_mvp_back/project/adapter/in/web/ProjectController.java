package com.data_arts_studio.web_mvp_back.project.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_arts_studio.web_mvp_back.project.adapter.in.web.request.ArchiveProjectRequest;
import com.data_arts_studio.web_mvp_back.project.adapter.in.web.request.CreateProjectRequest;
import com.data_arts_studio.web_mvp_back.project.adapter.in.web.response.ProjectResponse;
import com.data_arts_studio.web_mvp_back.project.application.port.in.ArchiveProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.ArchiveProjectUseCase;
import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final CreateProjectUseCase createProjectUseCase;
    private final ArchiveProjectUseCase archiveProjectUseCase;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        CreateProjectCommand command = CreateProjectCommand.builder()
            .name(request.name())
            .identifier(request.identifier())
            .identifierConfirm(request.identifierConfirm())
            .description(request.description())
            .ownerName(request.ownerName())
            .build();

        var result = createProjectUseCase.createProject(command);
        ProjectResponse response = new ProjectResponse(
                result.projectId(),
                result.name(),
                result.description(),
                result.ownerName(),
                result.createdAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{projectId}/archive")
    public ResponseEntity<Void> archiveProject(@PathVariable String projectId, @Valid @RequestBody ArchiveProjectRequest request) {
        ArchiveProjectCommand command = new ArchiveProjectCommand(projectId, request.confirmName());
        archiveProjectUseCase.archiveProject(command);
        return ResponseEntity.noContent().build();
    }
    
}
