package com.data_arts_studio.web_mvp_back.project.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.project.application.port.in.ArchiveProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.ArchiveProjectUseCase;
import com.data_arts_studio.web_mvp_back.project.application.port.out.LoadProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.validator.ProjectArchiveValidator;
import com.data_arts_studio.web_mvp_back.project.application.exception.ProjectBusinessException;
import com.data_arts_studio.web_mvp_back.project.application.exception.ProjectErrorCode;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectArchiveService implements ArchiveProjectUseCase {
    private final LoadProjectPort loadProjectPort;
    private final SaveProjectPort saveProjectPort;
    private final ProjectArchiveValidator projectArchiveValidator;

    @Override
    public void archiveProject(ArchiveProjectCommand command)  {
        Project project = loadProjectPort.loadById(new ProjectId(command.projectId()))
            .orElseThrow(() -> new ProjectBusinessException(ProjectErrorCode.PROJECT_NOT_FOUND));
        projectArchiveValidator.validate(project, command);
        project.markArchived();
        saveProjectPort.save(project);
    }
}