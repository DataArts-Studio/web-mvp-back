package com.data_arts_studio.web_mvp_back.test_run.application.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.test_run.application.exception.TestRunBusinessException;
import com.data_arts_studio.web_mvp_back.test_run.application.exception.TestRunErrorCode;

/**
 * 테스트 런 생성 요청의 유효성을 검증
 */
@Component
public class TestRunCreateValidator {

    /**
     * 테스트 런 생성 요청의 필수 입력값을 검증
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 런 이름
     * @param milestoneIds 선택된 마일스톤 식별자 목록
     */
    public void validate(String projectId, String name, List<String> milestoneIds) {
        validateName(name);
        validateMilestoneIds(milestoneIds);
        validateDuplicatedMilestoneIds(milestoneIds);
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
     * 마일스톤 선택 여부와 빈 식별자 포함 여부를 검증
     *
     * @param milestoneIds 선택된 마일스톤 식별자 목록
     */
    private void validateMilestoneIds(List<String> milestoneIds) {
        if (milestoneIds == null || milestoneIds.isEmpty()) {
            throw new TestRunBusinessException(TestRunErrorCode.TEST_RUN_MILESTONES_EMPTY);
        }
        if (milestoneIds.stream().anyMatch(milestoneId -> milestoneId == null || milestoneId.isBlank())) {
            throw new TestRunBusinessException(TestRunErrorCode.TEST_RUN_INVALID);
        }
    }

    /**
     * 하나의 테스트 런 안에 동일한 마일스톤이 중복 선택되지 않았는지 검증
     *
     * @param milestoneIds 선택된 마일스톤 식별자 목록
     */
    private void validateDuplicatedMilestoneIds(List<String> milestoneIds) {
        Set<String> distinctMilestoneIds = new HashSet<>();
        for (String milestoneId : milestoneIds) {
            // 같은 마일스톤이 두 번 이상 들어오면 차단 
            if (!distinctMilestoneIds.add(milestoneId)) {
                throw new TestRunBusinessException(TestRunErrorCode.TEST_RUN_MILESTONES_DUPLICATED);
            }
        }
    }

}
