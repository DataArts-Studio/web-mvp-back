package com.data_arts_studio.web_mvp_back.project.application.port.in;

import com.data_arts_studio.web_mvp_back.project.application.service.ProjectResult;

// 프로젝트 생성 유스케이스 인터페이스 
// 입력 포트
public interface CreateProjectUseCase {
    ProjectResult createProject(CreateProjectCommand command);
}   
