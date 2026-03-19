package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.util.List;

import lombok.Builder;

/**
 * 마일스톤에 포함된 테스트 케이스 목록 조회 결과
 *
 * @param milestoneId 기준 마일스톤 식별자
 * @param items 마일스톤에 포함된 테스트 케이스 목록
 */
@Builder
public record GetMilestoneTestCasesResult(
    String milestoneId, 
    List<GetMilestoneTestCaseItemResult> items) {
}
