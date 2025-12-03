package com.data_arts_studio.web_mvp_back.project.application.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectNamePort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.domain.Project;

class ProjectCreateServiceTest  {
    private CheckProjectNamePort checkProjectNamePort;
    private SaveProjectPort saveProjectPort;
    private ProjectCreateService service;

    @BeforeEach
    @DisplayName("테스트 초기화")
    void setUp() {
        checkProjectNamePort = mock(CheckProjectNamePort.class);
        saveProjectPort = mock(SaveProjectPort.class);
        service = new ProjectCreateService(checkProjectNamePort, saveProjectPort);
    }

    @Test
    @DisplayName("프로젝스 생성 성공 - 도메인 생성 되야하고, 저장 포트 호출, ProjectResult가 반환되어지는지 확인")
    void createProject_success() {
        // given
        CreateProjectCommand command = new CreateProjectCommand(
            "New Project",
            "설명",
            "uzi"
        );

        // when
        ProjectResult result = service.createProject(command);
        System.out.println("Created Project ID: " + result.toString());

        // then - save 호출되는지 확인
        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(saveProjectPort, times(1)).save(projectCaptor.capture());

        Project saved = projectCaptor.getValue();
        System.out.println("Saved Project ID: " + saved.getId().getId());
        System.out.println("Saved Project Name: " + saved.getName());
        System.out.println("Saved Project Owner: " + saved.getOwnerName());
        
        // 저장된 도메인 검증
        assertThat(saved.getName()).isEqualTo("New Project");
        assertThat(saved.getOwnerName()).isEqualTo("uzi");
        assertThat(saved.getDescription()).isEqualTo("설명");

        // 반환된 값 검증
        assertThat(result.getName()).isEqualTo("New Project");
        System.out.println("Returned Project Name: " + result.getName());
        assertThat(result.getOwnerName()).isEqualTo("uzi");
        System.out.println("Returned Project Owner: " + result.getOwnerName());
        assertThat(result.getDescription()).isEqualTo("설명");
        System.out.println("Returned Project Name: " + result.getName());
        assertThat(result.getProjectId()).isNotNull(); 
        System.out.println("Returned Project ID: " + result.getProjectId());
    }
}