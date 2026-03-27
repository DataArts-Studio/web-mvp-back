package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request;

/**
 * 마일스톤에 테스트 스위트를 연결하기 위한 요청 DTO
 *
 * @param testSuiteId 연결할 테스트 스위트 식별자
 */
public record AssignTestSuiteToMilestoneRequest(
    String testSuiteId) {
}
