package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.time.OffsetDateTime;

/**
 * 마일스톤 테스트 실행 이력의 단일 아이템 응답 DTO
 *
 * @param id 테스트 실행 식별자
 * @param name 테스트 실행 이름
 * @param createdAt 테스트 실행 생성 일시
 * @param status 테스트 실행 상태
 * @param progressPercent 테스트 실행 진행률
 */
public record GetMilestoneTestRunItemResponse(
        String id,
        String name,
        OffsetDateTime createdAt,
        String status,
        int progressPercent) {
}
