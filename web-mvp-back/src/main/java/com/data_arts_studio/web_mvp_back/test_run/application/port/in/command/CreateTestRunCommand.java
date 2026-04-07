package com.data_arts_studio.web_mvp_back.test_run.application.port.in.command;

import lombok.Builder;

/**
 * 테스트 런 생성 요청을 표현하는 커맨드
 *
 * @param projectId 프로젝트 식별자
 * @param milestoneId 기준 마일스톤 식별자
 * @param name 테스트 실행 이름
 * @param description 테스트 실행 설명
 */
@Builder
public record CreateTestRunCommand(
        String projectId,
        String milestoneId,
        String name,
        String description) {
}
