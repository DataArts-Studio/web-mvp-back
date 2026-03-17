package com.data_arts_studio.web_mvp_back.milestone.application.port.in.command;

import java.time.OffsetDateTime;

import lombok.Builder;

/**
 * 마일스톤 생성 요청을 표현하는 커맨드
 *
 * @param projectId 마일스톤이 속할 프로젝트 식별자
 * @param name 마일스톤 이름
 * @param description 마일스톤 설명
 * @param startDate 시작일
 * @param endDate 종료일
 */
@Builder
public record CreateMilestoneCommand(
        String projectId,
        String name,
        String description,
        OffsetDateTime startDate,
        OffsetDateTime endDate) {
}
