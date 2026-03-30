package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

/**
 * 마일스톤 테스트 스위트 목록의 단일 아이템 응답 DTO
 *
 * @param id 테스트 스위트 식별자
 * @param name 테스트 스위트 이름
 * @param description 테스트 스위트 설명
 * @param linkedTestCaseCount 연결된 테스트 케이스 수
 */
public record GetMilestoneTestSuiteItemResponse(
        String id,
        String name,
        String description,
        int linkedTestCaseCount) {
}
