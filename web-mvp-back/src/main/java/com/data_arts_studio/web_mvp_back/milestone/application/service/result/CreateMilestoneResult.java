package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.time.OffsetDateTime;

import lombok.Builder;

/**
 * 마일스톤 생성 결과 
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
