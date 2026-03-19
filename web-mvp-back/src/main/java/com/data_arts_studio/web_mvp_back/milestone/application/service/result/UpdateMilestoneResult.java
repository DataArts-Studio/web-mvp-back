package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.time.OffsetDateTime;

import lombok.Builder;

/**
 * 마일스톤 수정 결과
 *
 * @param id 수정된 마일스톤 식별자
 * @param projectId 프로젝트 식별자
 * @param name 수정된 마일스톤 이름
 * @param description 수정된 마일스톤 설명
 * @param status 수정된 마일스톤 상태
 * @param startDate 수정된 시작 일시
 * @param endDate 수정된 종료 일시
 * @param updatedAt 최종 수정 일시
 */
@Builder
public record UpdateMilestoneResult(
        String id,
        String projectId,
        String name,
        String description,
        String status,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        OffsetDateTime updatedAt) {
}
