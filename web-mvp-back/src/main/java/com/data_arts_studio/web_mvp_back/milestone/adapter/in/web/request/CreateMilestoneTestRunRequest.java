package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request;

/**
 * 마일스톤 기반 테스트 실행 생성 요청 DTO
 *
 * @param projectId 프로젝트 식별자
 * @param milestoneId 기준 마일스톤 식별자
 * @param name 테스트 실행 이름
 * @param description 테스트 실행 설명
 */
public record CreateMilestoneTestRunRequest(
        String projectId,
        String milestoneId,
        String name,
        String description) {
}
