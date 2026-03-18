package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

/**
 * 마일스톤 기능에서 프로젝트 존재 여부를 확인하기 위한 포트
 */
public interface CheckProjectExistsPort {

    /**
     * 활성 프로젝트 존재 여부를 확인
     *
     * @param projectId 프로젝트 식별자
     * @return 존재 여부
     */
    boolean existsById(String projectId);
}
