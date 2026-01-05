package com.data_arts_studio.web_mvp_back.project.application.port.out;

import java.util.Optional;

import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

public interface LoadProjectPort {
    /**
     * 식별자를 통해 프로젝트 도메인 엔티티를 로드
     * @param projectId 도메인 값 객체 식별자
     * @return 검색된 프로젝트 (없을 경우 Optional.empty())
     */
    Optional<Project> loadById(ProjectId projectId);
}
