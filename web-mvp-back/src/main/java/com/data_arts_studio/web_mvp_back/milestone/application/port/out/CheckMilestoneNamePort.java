package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

/**
 * 프로젝트 범위의 마일스톤 이름 중복 검사용 포트
 */
public interface CheckMilestoneNamePort {

    /**
     * 같은 프로젝트 내 이름 중복 여부를 확인
     *
     * @param projectId 프로젝트 식별자
     * @param name 마일스톤 이름
     * @return 중복 여부
     */
    boolean isMilestoneNameDuplicated(String projectId, String name);

    /**
     * 특정 마일스톤을 제외하고 같은 프로젝트 내 이름 중복 여부를 확인
     *
     * @param projectId 프로젝트 식별자
     * @param name 마일스톤 이름
     * @param excludeMilestoneId 제외할 마일스톤 식별자
     * @return 중복 여부
     */
    boolean isMilestoneNameDuplicatedExceptId(String projectId, String name, String excludeMilestoneId);
}

