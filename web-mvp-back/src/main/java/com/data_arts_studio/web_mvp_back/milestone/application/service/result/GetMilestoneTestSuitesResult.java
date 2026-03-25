package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.util.List;

import lombok.Builder;

/**
 * 마일스톤에 연결된 테스트 스위트 목록 조회 결과
 *
 * @param milestoneId 기준 마일스톤 식별자
 * @param items 마일스톤에 연결된 테스트 스위트 목록
 */
@Builder
public record GetMilestoneTestSuitesResult(
    String milestoneId, 
    List<GetMilestoneTestSuiteItemResult> items) {
}
