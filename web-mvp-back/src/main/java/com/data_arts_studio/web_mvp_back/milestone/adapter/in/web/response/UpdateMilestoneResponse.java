package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.time.OffsetDateTime;

/**
 * 마일스톤 수정 응답 DTO
 *
 * @param id 마일스톤 식별자
 * @param projectId 소속 프로젝트 식별자
 * @param name 수정된 마일스톤 이름
 * @param description 수정된 마일스톤 설명
 * @param status 마일스톤 진행 상태
 * @param startDate 수정된 시작일
 * @param endDate 수정된 종료일
 * @param updatedAt 최종 수정 시각
 */
public record UpdateMilestoneResponse(
        String id,
        String projectId,
        String name,
        String description,
        String status,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        OffsetDateTime updatedAt) {
}
