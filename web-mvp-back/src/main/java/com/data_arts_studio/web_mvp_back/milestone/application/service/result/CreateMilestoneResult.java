package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.time.OffsetDateTime;

import lombok.Builder;

/**
 * 마일스톤 생성 결과
 *
 * @param id 생성된 마일스톤 식별자
 * @param projectId 프로젝트 식별자
 * @param name 생성된 마일스톤 이름
 * @param description 생성된 마일스톤 설명
 * @param status 생성된 마일스톤 상태
 * @param startDate 시작 일시
 * @param endDate 종료 일시
 * @param createdAt 생성 일시
 */
@Builder
public record CreateMilestoneResult(
        String id,
        String projectId,
        String name,
        String description,
        String status,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        OffsetDateTime createdAt) {
}
