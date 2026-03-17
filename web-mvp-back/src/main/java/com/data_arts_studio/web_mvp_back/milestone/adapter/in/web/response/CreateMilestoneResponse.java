package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.time.OffsetDateTime;

/**
 * 마일스톤 생성 응답 DTO
 *
 * @param id 마일스톤 식별자
 * @param projectId 소속 프로젝트 식별자
 * @param name 마일스톤 이름
 * @param description 마일스톤 설명
 * @param status 마일스톤 진행 상태
 * @param startDate 시작일
 * @param endDate 종료일
 * @param createdAt 생성 시각
 */
public record CreateMilestoneResponse(
        String id,
        String projectId,
        String name,
        String description,
        String status,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        OffsetDateTime createdAt) {
}