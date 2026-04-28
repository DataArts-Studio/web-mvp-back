package com.data_arts_studio.web_mvp_back.test_run.application.port.in.command;

import java.util.List;
import lombok.Builder;

/**
 * 테스트 런 생성 요청을 표현하는 커맨드
 *
 * @param projectId 프로젝트 식별자
 * @param milestoneIds 기준 마일스톤 식별자 목록
 * @param name 테스트 실행 이름
 * @param description 테스트 실행 설명
 */
@Builder
public record CreateTestRunCommand(
        String projectId,
        List<String> milestoneIds,
        String name,
        String description) {
}
