package com.data_arts_studio.web_mvp_back.project.application.port.in;

import java.time.LocalDateTime;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectStatus;

// 프로젝트 생성 요청 시에 필요한 커맨드 객체 (입력)
public record CreateProjectCommand(
    String name,
    String password, 
    String passwordConfirm,// 비밀번호 확인
    String description,
    String ownerName,
    ProjectStatus status,
    LocalDateTime createdAt
) {
    
}
