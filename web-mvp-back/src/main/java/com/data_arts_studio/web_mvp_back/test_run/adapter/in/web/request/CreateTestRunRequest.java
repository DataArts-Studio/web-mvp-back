package com.data_arts_studio.web_mvp_back.test_run.adapter.in.web.request;

import java.util.List;

/**
 * 테스트 런 생성 요청 DTO
 *
 * @param projectId 프로젝트 식별자
 * @param milestoneIds 기준 마일스톤 식별자 목록
 * @param name 테스트 실행 이름
 * @param description 테스트 실행 설명
 */
public record CreateTestRunRequest(
        String projectId,
        List<String> milestoneIds,
        String name,
        String description) {
}
