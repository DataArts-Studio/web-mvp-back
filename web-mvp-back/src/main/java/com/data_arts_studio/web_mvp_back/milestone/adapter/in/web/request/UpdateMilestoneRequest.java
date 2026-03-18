package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request;

import java.time.OffsetDateTime;

/**
 * 마일스톤 수정 요청 DTO
 *
 * @param name 수정할 이름
 * @param description 수정할 설명
 * @param startDate 수정할 시작일
 * @param endDate 수정할 종료일
 */
public record UpdateMilestoneRequest(
        String name,
        String description,
        OffsetDateTime startDate,
        OffsetDateTime endDate) {
}
