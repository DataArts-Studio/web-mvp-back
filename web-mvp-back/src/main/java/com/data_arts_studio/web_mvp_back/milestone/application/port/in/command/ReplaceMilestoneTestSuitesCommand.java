package com.data_arts_studio.web_mvp_back.milestone.application.port.in.command;

import java.util.List;

import lombok.Builder;

/**
 * 마일스톤에 연결된 테스트 스위트 목록을 최종 상태로 전체 교체하기 위한 커맨드
 *
 * @param milestoneId 마일스톤 식별자
 * @param testSuiteIds 최종 연결할 테스트 스위트 식별자 목록
 */
@Builder
public record ReplaceMilestoneTestSuitesCommand(
    String milestoneId,
    List<String> testSuiteIds) {
}
