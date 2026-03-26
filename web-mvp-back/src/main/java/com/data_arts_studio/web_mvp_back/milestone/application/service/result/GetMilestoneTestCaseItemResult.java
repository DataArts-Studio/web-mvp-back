package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import lombok.Builder;

/**
 * 마일스톤에 포함된 테스트 케이스 목록의 단일 목록 
 * 
 * @param id 테스트 케이스 식별자
 * @param caseKey 화면 표시용 테스트 케이스 키
 * @param name 테스트 케이스 이름
 * @param latestResultStatus 가장 최근 테스트 결과 상태
 */
@Builder
public record GetMilestoneTestCaseItemResult(
        String id,
        String caseKey,
        String name,
        String latestResultStatus) {
}
