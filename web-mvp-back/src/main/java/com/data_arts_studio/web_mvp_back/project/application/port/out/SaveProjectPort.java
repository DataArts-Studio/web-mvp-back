package com.data_arts_studio.web_mvp_back.project.application.port.out;

import com.data_arts_studio.web_mvp_back.project.domain.Project;

// 프로젝트 저장 관련 포트 인터페이스
public interface SaveProjectPort {
    /**
     * 프로젝트 생성
     * @param project 생성할 프로젝트 도메인 객체
     */
    void createProject(Project project);

    /**
     * 프로젝트 수정
     * @param project 수정할 프로젝트 도메인 객체
     */
    void updateProject(Project project);
} 