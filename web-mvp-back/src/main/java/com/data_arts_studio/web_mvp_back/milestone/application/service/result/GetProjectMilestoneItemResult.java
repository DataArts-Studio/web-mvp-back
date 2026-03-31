package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.time.OffsetDateTime;
import lombok.Builder;

/**
 * 프로젝트 마일스톤 목록의 단일 아이템 조회 모델
 *
 * @param id 마일스톤 식별자
 * @param projectId 프로젝트 식별자
 * @param name 마일스톤 이름
 * @param descriptionPreview 마일스톤 설명 요약
 * @param status 마일스톤 상태
 * @param startDate 시작 일시
 * @param endDate 종료 일시
 * @param progressPercent 진행률
 * @param totalCaseCount 전체 테스트 케이스 수
 * @param completedCaseCount 완료된 테스트 케이스 수
 * @param testRunCount 테스트 실행 수
 */
@Builder
public record GetProjectMilestoneItemResult(
        String id,
        String projectId,
        String name,
        String descriptionPreview,
        String status,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        int progressPercent,
        int totalCaseCount,
        int completedCaseCount,
        int testRunCount) {
}
