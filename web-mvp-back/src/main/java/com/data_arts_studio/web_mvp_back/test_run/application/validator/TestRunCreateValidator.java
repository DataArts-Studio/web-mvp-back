package com.data_arts_studio.web_mvp_back.test_run.application.validator;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.test_run.application.exception.TestRunBusinessException;
import com.data_arts_studio.web_mvp_back.test_run.application.exception.TestRunErrorCode;
import com.data_arts_studio.web_mvp_back.test_run.application.port.out.CheckTestRunNamePort;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 런 생성 요청의 유효성을 검증
 */
@Component
@RequiredArgsConstructor
public class TestRunCreateValidator {
    private final CheckTestRunNamePort checkTestRunNamePort;

    /**
     * 테스트 런 생성 요청의 필수 입력값과 이름 중복 여부를 검증
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 런 이름
     * @param milestoneId 선택된 마일스톤 식별자
     */
    public void validate(String projectId, String name, String milestoneId) {
        validateName(name);
        validateMilestoneId(milestoneId);
        validateNameDuplicated(projectId, name);
    }

    /**
     * 선택된 마일스톤이 요청 프로젝트에 속하는지 검증
     *
     * @param projectId 프로젝트 식별자
     * @param milestone 검증 대상 마일스톤
     */
    public void validateMilestoneBelongsToProject(String projectId, Milestone milestone) {
        if (!milestone.getProjectId().getId().equals(projectId)) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND);
        }
    }

    /**
     * 테스트 런 이름의 필수 여부를 검증
     *
     * @param name 테스트 런 이름
     */
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new TestRunBusinessException(TestRunErrorCode.TEST_RUN_NAME_EMPTY);
        }
    }

    /**
     * 마일스톤 선택 여부를 검증
     *
     * @param milestoneId 선택된 마일스톤 식별자
     */
    private void validateMilestoneId(String milestoneId) {
        if (milestoneId == null || milestoneId.isBlank()) {
            throw new TestRunBusinessException(TestRunErrorCode.TEST_RUN_MILESTONES_EMPTY);
        }
    }

    /**
     * 같은 프로젝트 내 테스트 런 이름 중복 여부를 검증
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 런 이름
     */
    private void validateNameDuplicated(String projectId, String name) {
        if (checkTestRunNamePort.isTestRunNameDuplicated(projectId, name)) {
            throw new TestRunBusinessException(TestRunErrorCode.TEST_RUN_NAME_DUPLICATED);
        }
    }
}
