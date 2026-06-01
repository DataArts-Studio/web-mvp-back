package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.util.List;

import lombok.Builder;

/**
 * 마일스톤 기반 테스트 실행 이력 조회 결과
 * 
 * @param milestoneId 마일스톤 식별자
 * @param items       테스트 실행 이력 항목 목록
 */
@Builder
public record GetMilestoneTestRunsResult(
    String milestoneId, 
    List<GetMilestoneTestRunItemResult> items) {
}
