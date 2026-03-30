package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import lombok.Builder;

/**
 * 마일스톤에 연결된 테스트 스위트 목록의 단일 아이템
 *
 * @param id 테스트 스위트 식별자
 * @param name 테스트 스위트 이름
 * @param description 테스트 스위트 설명
 * @param linkedTestCaseCount 연결된 테스트 케이스 수
 */
@Builder
public record GetMilestoneTestSuiteItemResult(
        String id,
        String name,
        String description,
        int linkedTestCaseCount) {
}
