package com.data_arts_studio.web_mvp_back.project.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.project.application.port.in.ArchiveProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.ArchiveProjectUseCase;
import com.data_arts_studio.web_mvp_back.project.application.port.out.LoadProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectArchiveService implements ArchiveProjectUseCase {
    private final LoadProjectPort loadProjectPort;
    private final SaveProjectPort saveProjectPort;

    @Override
    public void archiveProject(ArchiveProjectCommand command)  {
        Project project = loadProjectPort.loadById(new ProjectId(command.projectId()))
            .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: " + command.projectId()));
        project.archive(command.confirmName());
        saveProjectPort.save(project);
    }
}
