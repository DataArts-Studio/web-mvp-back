package com.data_arts_studio.web_mvp_back.milestone.application.port.in.command;

import lombok.Builder;

/**
 * 마일스톤에 테스트 케이스 한 건을 연결하기 위한 커맨드
 *
 * @param milestoneId 마일스톤 식별자
 * @param testCaseId 연결할 테스트 케이스 식별자
 */
@Builder
public record AssignTestCaseToMilestoneCommand(
    String milestoneId, 
    String testCaseId) {
}
