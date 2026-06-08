package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.util.List;

/**
 * 마일스톤 테스트 실행 이력 응답 DTO
 *
 * @param milestoneId 기준 마일스톤 식별자
 * @param items 마일스톤 테스트 실행 이력 목록
 */
public record GetMilestoneTestRunsResponse(
    String milestoneId, 
    List<GetMilestoneTestRunItemResponse> items) {
}
