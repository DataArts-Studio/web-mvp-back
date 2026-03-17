package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request;

import java.time.OffsetDateTime;

/**
 * 마일스톤 생성 요청 DTO
 *
 * @param name 마일스톤 이름
 * @param description 마일스톤 설명
 * @param startDate 시작일
 * @param endDate 종료일
 */
public record CreateMilestoneRequest(
        String name,
        String description,
        OffsetDateTime startDate,
        OffsetDateTime endDate) {
}
