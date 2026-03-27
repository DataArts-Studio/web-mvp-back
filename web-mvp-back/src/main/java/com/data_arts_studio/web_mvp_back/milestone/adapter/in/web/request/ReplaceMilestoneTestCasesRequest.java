package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request;

import java.util.List;

/**
 * 마일스톤 테스트 케이스 범위를 전체 교체하기 위한 요청 DTO
 *
 * @param testCaseIds 최종 테스트 케이스 식별자 목록
 */
public record ReplaceMilestoneTestCasesRequest(
    List<String> testCaseIds) {
}
