package com.data_arts_studio.web_mvp_back.project.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectNamePort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.validator.ProjectBusinessException;
import com.data_arts_studio.web_mvp_back.project.application.validator.ProjectCreateValidator;
import com.data_arts_studio.web_mvp_back.project.domain.Project;

import org.springframework.security.crypto.password.PasswordEncoder;

class ProjectCreateServiceTest {

    private ProjectCreateService projectCreateService;
    private CheckProjectNamePort checkProjectNamePort;
    private SaveProjectPort saveProjectPort;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // MOCK 인터페이스 생성
        checkProjectNamePort = mock(CheckProjectNamePort.class);
        saveProjectPort = mock(SaveProjectPort.class);
        passwordEncoder = mock(PasswordEncoder.class);

        when(passwordEncoder.encode(any())).thenReturn("encoded_pw");

        ProjectCreateValidator validator = new ProjectCreateValidator(checkProjectNamePort);
        // 서비스 인스턴스 생성
        projectCreateService = new ProjectCreateService(
                saveProjectPort,
                passwordEncoder,
                validator
        );
    }

    @Test
    void 프로젝트_이름_null이면_예외() {
        CreateProjectCommand command = new CreateProjectCommand(
                null, "1234", "1234", "설명", "uzi", LocalDateTime.now()
        );

        assertThatThrownBy(() -> projectCreateService.createProject(command))
                .isInstanceOf(ProjectBusinessException.class);
    }

    @Test
    void 프로젝트_중복이면_예외() {
        CreateProjectCommand command = new CreateProjectCommand(
                "Project", "1234", "1234", "설명", "uzi", LocalDateTime.now()
        );

        when(checkProjectNamePort.isProjectNameDuplicated("Project"))
                .thenReturn(true);

        assertThatThrownBy(() -> projectCreateService.createProject(command))
                .isInstanceOf(ProjectBusinessException.class);
    }
    @Test
    void 비밀번호_null이면_예외() {
        CreateProjectCommand command = new CreateProjectCommand(
                "Project", null, null, "설명", "uzi", LocalDateTime.now()
        );

        assertThatThrownBy(() -> projectCreateService.createProject(command))
                .isInstanceOf(ProjectBusinessException.class);
    }
    
    @Test
    void 비밀번호_불일치시_예외() {
        CreateProjectCommand command = new CreateProjectCommand(
                "Project", "1234", "5678", "설명", "uzi", LocalDateTime.now()
        );

        assertThatThrownBy(() -> projectCreateService.createProject(command))
                .isInstanceOf(ProjectBusinessException.class);
    }

    @Test
    void 정상입력이면_프로젝트_생성된다() {
        CreateProjectCommand command = new CreateProjectCommand(
                "Project", "1234", "1234", "설명", "uzi", LocalDateTime.now()
        );

        when(checkProjectNamePort.isProjectNameDuplicated("Project"))
                .thenReturn(false);

        projectCreateService.createProject(command);

        verify(saveProjectPort, times(1)).save(any(Project.class));
    }
}