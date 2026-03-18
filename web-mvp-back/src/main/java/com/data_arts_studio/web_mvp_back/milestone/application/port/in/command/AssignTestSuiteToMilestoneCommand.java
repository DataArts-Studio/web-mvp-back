package com.data_arts_studio.web_mvp_back.milestone.application.port.in.command;

import lombok.Builder;

/**
 * 마일스톤에 테스트 스위트 한 건을 연결하기 위한 커맨드
 *
 * @param milestoneId 마일스톤 식별자
 * @param testSuiteId 연결할 테스트 스위트 식별자
 */
@Builder
public record AssignTestSuiteToMilestoneCommand(
    String milestoneId, 
    String testSuiteId) {
}
