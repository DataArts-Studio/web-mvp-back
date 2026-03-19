package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import lombok.Builder;

/**
 * 마일스톤 기반 테스트 실행 생성 결과
 *
 * @param id 생성된 테스트 실행 식별자
 * @param projectId 프로젝트 식별자
 * @param milestoneId 기준 마일스톤 식별자
 * @param name 생성된 테스트 실행 이름
 * @param status 생성된 테스트 실행 상태
 */
@Builder
public record CreateMilestoneTestRunResult(
        String id,
        String projectId,
        String milestoneId,
        String name,
        String status) {
}
