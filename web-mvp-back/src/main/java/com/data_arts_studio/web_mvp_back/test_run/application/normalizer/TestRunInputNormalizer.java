package com.data_arts_studio.web_mvp_back.test_run.application.normalizer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_run.application.port.in.command.CreateTestRunCommand;

/**
 * 테스트 런 생성 입력값을 정규화
 */
@Component
public class TestRunInputNormalizer {

    /**
     * 테스트 런 생성 커맨드의 문자열 필드를 정규화
     * - 이름과 마일스톤 식별자는 null을 빈 문자열로 변환하고 trim 
     * - 설명은 null을 유지하되 trim 후 빈 문자열이면 null로 변환
     * 
     * @param command 원본 생성 요청
     * @return 정규화된 생성 요청
     */
    public CreateTestRunCommand normalize(CreateTestRunCommand command) {
        return CreateTestRunCommand.builder()
                .projectId(command.projectId())
                .milestoneIds(normalizeMilestoneIds(command.milestoneIds()))
                .name(normalizeName(command.name()))
                .description(normalizeDescription(command.description()))
                .build();
    }

    /**
     * 테스트 런 이름을 정규화
     *
     * @param name 원본 이름
     * @return trim 된 이름
     */
    private String normalizeName(String name) {
        return name == null ? "" : name.trim();
    }

    /**
     * 테스트 런 설명을 정규화
     *
     * @param description 원본 설명
     * @return trim 된 설명, 비어 있으면 null
     */
    private String normalizeDescription(String description) {
        if (description == null) {
            return null;
        }
        String trimmed = description.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    /**
     * 마일스톤 식별자 목록을 정규화
     *
     * 마일스톤 선택은 순서와 중복 검증이 의미 있으므로
     * 정규화 단계에서는 trim 만 적용하고 구조는 그대로 유지
     *
     * @param milestoneIds 원본 마일스톤 식별자 목록
     * @return trim 이 적용된 마일스톤 식별자 목록
     */
    private List<String> normalizeMilestoneIds(List<String> milestoneIds) {
        if (milestoneIds == null) {
            return List.of();
        }
        return milestoneIds.stream()
                .map(milestoneId -> milestoneId == null ? "" : milestoneId.trim())
                .toList();
    }
}
