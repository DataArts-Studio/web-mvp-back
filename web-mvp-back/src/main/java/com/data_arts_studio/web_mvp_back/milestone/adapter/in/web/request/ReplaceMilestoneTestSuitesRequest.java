package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request;

import java.util.List;

/**
 * 마일스톤 테스트 스위트 범위를 전체 교체하기 위한 요청 DTO
 *
 * @param testSuiteIds 최종 테스트 스위트 식별자 목록
 */
public record ReplaceMilestoneTestSuitesRequest(
    List<String> testSuiteIds) {
}
