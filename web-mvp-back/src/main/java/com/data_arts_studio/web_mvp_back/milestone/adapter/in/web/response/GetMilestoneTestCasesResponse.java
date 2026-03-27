package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.util.List;

/**
 * 마일스톤 테스트 케이스 목록 응답 DTO
 *
 * @param milestoneId 기준 마일스톤 식별자
 * @param items 마일스톤에 포함된 테스트 케이스 목록
 */
public record GetMilestoneTestCasesResponse(
    String milestoneId, 
    List<GetMilestoneTestCaseItemResponse> items) {
}
