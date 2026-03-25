package com.data_arts_studio.web_mvp_back.milestone.application.port.in.command;

import lombok.Builder;

/**
 * 마일스톤에서 테스트 스위트 연결 한 건을 해제하기 위한 커맨드
 *
 * @param milestoneId 마일스톤 식별자
 * @param testSuiteId 연결 해제할 테스트 스위트 식별자
 */
@Builder
public record RemoveTestSuiteFromMilestoneCommand(
    String milestoneId, 
    String testSuiteId) {
}
