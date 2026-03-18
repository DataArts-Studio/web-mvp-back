package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request;

/**
 * 마일스톤에 테스트 케이스를 연결하기 위한 요청 DTO
 *
 * @param testCaseId 연결할 테스트 케이스 식별자
 */
public record AssignTestCaseToMilestoneRequest(
    String testCaseId) {
}
