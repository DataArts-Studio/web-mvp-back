package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.time.OffsetDateTime;

import lombok.Builder;

/**
 * 마일스톤 기반 테스트 실행 이력의 단일 아이템 
 * 
 * @param id              테스트 실행 이력 ID
 * @param name            테스트 실행 이력 이름
 * @param createdAt       테스트 실행 이력 생성 일시
 * @param status          테스트 실행 이력 상태
 * @param progressPercent 테스트 실행 이력 진행률   
 */
@Builder
public record GetMilestoneTestRunItemResult(
        String id,
        String name,
        OffsetDateTime createdAt,
        String status,
        int progressPercent) {
}
