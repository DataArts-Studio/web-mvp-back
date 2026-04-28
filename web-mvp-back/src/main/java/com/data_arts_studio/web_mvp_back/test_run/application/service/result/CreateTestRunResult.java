package com.data_arts_studio.web_mvp_back.test_run.application.service.result;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;

/**
 * 테스트 런 생성 결과
 *
 * @param id 생성된 테스트 런 식별자
 * @param projectId 프로젝트 식별자
 * @param milestoneIds 기준 마일스톤 식별자 목록
 * @param name 생성된 테스트 런 이름
 * @param description 생성된 테스트 런 설명
 * @param status 생성된 테스트 런 상태
 * @param createdAt 생성 시각
 * @param updatedAt 수정 시각
 * @param archivedAt 아카이브 시각
 * @param lifecycleStatus 라이프사이클 상태
 * @param shareToken 공유 토큰
 * @param shareExpiresAt 공유 만료 시각
//  * @param shareAiSummary 공유용 AI 요약
 */
@Builder
public record CreateTestRunResult(
        String id,
        String projectId,
        List<String> milestoneIds,
        String name,
        String description,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime archivedAt,
        String lifecycleStatus,
        String shareToken,
        OffsetDateTime shareExpiresAt
       // ,String shareAiSummary
        ) {
}
