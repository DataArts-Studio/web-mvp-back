package com.data_arts_studio.web_mvp_back.milestone.application.port.in.command;

import java.time.OffsetDateTime;

import lombok.Builder;

/**
 * 마일스톤 기본 정보를 수정하기 위한 커맨드
 *
 * @param milestoneId 수정 대상 마일스톤 식별자
 * @param name 수정할 이름
 * @param description 수정할 설명
 * @param startDate 수정할 시작일
 * @param endDate 수정할 종료일
 */
@Builder
public record UpdateMilestoneCommand(
        String milestoneId,
        String name,
        String description,
        OffsetDateTime startDate,
        OffsetDateTime endDate) {
}
