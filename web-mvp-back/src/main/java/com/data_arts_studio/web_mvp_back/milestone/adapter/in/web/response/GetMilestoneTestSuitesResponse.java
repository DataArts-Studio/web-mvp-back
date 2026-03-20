package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.util.List;

/**
 * 마일스톤 테스트 스위트 목록 응답 DTO
 *
 * @param milestoneId 기준 마일스톤 식별자
 * @param items 마일스톤에 연결된 테스트 스위트 목록
 */
public record GetMilestoneTestSuitesResponse(
    String milestoneId, 
    List<GetMilestoneTestSuiteItemResponse> items) {
}
