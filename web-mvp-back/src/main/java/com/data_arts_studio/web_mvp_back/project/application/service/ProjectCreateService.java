package com.data_arts_studio.web_mvp_back.project.application.service;

import org.springframework.stereotype.Service;

import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectUseCase;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectSlugPort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.validator.ProjectCreateValidator;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectSlug;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


/** 프로젝트 생성 서비스 구현체 */
@Service
@RequiredArgsConstructor
public class ProjectCreateService implements CreateProjectUseCase {
    private final SaveProjectPort saveProjectPort;
    private final CheckProjectSlugPort checkProjectSlugPort;
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
        ProjectSlug slug = generateUniqueSlug(command.name());
        Project project = new Project(projectId, command.name(), slug ,hashedPassword, command.description(),command.ownerName());
        saveProjectPort.save(project);
        return new ProjectResult(projectId.getId(), project.getSlug().getValue(), project.getName(), project.getDescription(), project.getOwnerName(), project.getCreatedAt());
    }

    /**
     * 프로젝트 이름 기반으로 고유한 slug 생성 메서드
     * 1. ProjectSlug 내부 로직으로 1차 slug 셍성
     * 2. DB 기준 중복 여부를 확인 
     * 3. 존재한다면 다시 생성 
     * @param name
     * @return DB를 기준으로 고유한 slug obj
     */
    private ProjectSlug generateUniqueSlug(String name) {
        ProjectSlug slug;
        do {
            slug = ProjectSlug.fromProjectNameGenerateProjectSlug(name);
        } while (checkProjectSlugPort.existsBySlug(slug.getValue()));
        return slug;
    } 
}
