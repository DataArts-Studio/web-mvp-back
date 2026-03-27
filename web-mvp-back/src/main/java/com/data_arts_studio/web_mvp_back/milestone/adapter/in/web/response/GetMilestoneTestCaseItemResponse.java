package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

/**
 * 마일스톤 테스트 케이스 목록의 단일 아이템 응답 DTO
 *
 * @param id                 테스트 케이스 고유 식별자
 * @param caseKey            화면 표시용 테스트 케이스 키 
 * @param name               테스트 케이스 이름 (제목)
 * @param latestResultStatus 가장 최근에 실행된 테스트 결과 상태 
 */
public record GetMilestoneTestCaseItemResponse(
        String id,
        String caseKey,
        String name,
        String latestResultStatus) {
}