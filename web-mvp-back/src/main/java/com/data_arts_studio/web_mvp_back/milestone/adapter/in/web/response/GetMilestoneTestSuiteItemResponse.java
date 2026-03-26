package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

/**
 * 마일스톤 테스트 스위트 목록의 단일 아이템 응답 DTO이다.
 *
 * @param id 테스트 스위트 식별자
 * @param name 테스트 스위트 이름
 */
public record GetMilestoneTestSuiteItemResponse(
        String id,
        String name) {
}
