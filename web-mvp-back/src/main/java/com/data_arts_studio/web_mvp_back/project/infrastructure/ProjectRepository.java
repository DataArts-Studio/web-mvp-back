package com.data_arts_studio.web_mvp_back.project.infrastructure;

import java.util.List;
import java.util.Optional;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

public interface ProjectRepository {

    /** 프로젝트 생성 저장 메서드
     * @param 저장할 프로젝트 도메인 객체
     */
    Project save(Project project);

    /** 프로젝트 아이디로 조회 메서드 
     * @param 도메인 ID (ProjectId)
     */ 
    Optional<Project> findById(ProjectId id);

    // 모든 프로젝트 조회 메서드
    List<Project> findAll();

    /** 프로젝트 soft delete 메서드 
     * 도메인에서 soft-delete를 사용하므로 포트도 softDelete로 노출
     * @param 삭제할 프로젝트 id (ProjectId)
     */
    void softDelete(ProjectId id);
}
