package com.data_arts_studio.web_mvp_back.project.application.port.out;

import com.data_arts_studio.web_mvp_back.project.domain.Project;

// 프로젝트 아카이브 관련 아웃 포트 인터페이스
public interface ArchiveProjectPort {
    /**
     * 프로젝트 아카이브
     * @param project 아카이브할 프로젝트 도메인 객체
     */
    void archive(Project project);
}
