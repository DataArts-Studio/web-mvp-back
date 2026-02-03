package com.data_arts_studio.web_mvp_back.project.application.port.in;

// 프로젝트 아카이브 요청 시에 필요한 커맨드 객체 
public record ArchiveProjectCommand(
    String projectId,
    String confirmName // 사용자가 확인을 위해 입력할 프로젝트 이름
) {}
