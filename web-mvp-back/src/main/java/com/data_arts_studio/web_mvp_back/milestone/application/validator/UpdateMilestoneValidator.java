package com.data_arts_studio.web_mvp_back.milestone.application.validator;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.UpdateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.CheckMilestoneNamePort;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 수정 요청의 유효성을 검증
 */
@Component
@RequiredArgsConstructor
public class UpdateMilestoneValidator {
    private final CheckMilestoneNamePort checkMilestoneNamePort;

    /**
     * 마일스톤 수정 요청의 이름, 설명, 일정, 이름 중복 여부를 검증
     *
     * @param command 수정 요청 정보
     * @param projectId 마일스톤이 속한 프로젝트 식별자
     */
    public void validate(UpdateMilestoneCommand command, String projectId) {
        validateName(command.name());
        validateDescription(command.description());
        validateDateRange(command.startDate(), command.endDate());
        validateNameDuplicated(projectId, command.name().trim(), command.milestoneId());
    }

    /**
     * 마일스톤 이름의 필수 여부와 최대 길이를 검증
     *
     * @param name 마일스톤 이름
     */
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NAME_EMPTY);
        }
        if (name.trim().length() > 50) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NAME_TOO_LONG);
        }
    }

    /**
     * 마일스톤 설명의 최대 길이를 검증
     *
     * @param description 마일스톤 설명
     */
    private void validateDescription(String description) {
        if (description != null && description.length() > 500) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_DESCRIPTION_TOO_LONG);
        }
    }

    /**
     * 시작일이 종료일보다 늦지 않도록 일정 범위를 검증
     *
     * @param startDate 시작일
     * @param endDate 종료일
     */
    private void validateDateRange(java.time.OffsetDateTime startDate, java.time.OffsetDateTime endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new MilestoneBusinessException(MilestoneErrorCode.INVALID_DATE_RANGE);
        }
    }

    /**
     * 같은 프로젝트 내에서 현재 마일스톤을 제외한 이름 중복 여부를 검증
     *
     * @param projectId 프로젝트 식별자
     * @param name 마일스톤 이름
     * @param milestoneId 수정 대상 마일스톤 식별자
     */
    private void validateNameDuplicated(String projectId, String name, String milestoneId) {
        if (checkMilestoneNamePort.isMilestoneNameDuplicatedExceptId(projectId, name, milestoneId)) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NAME_DUPLICATED);
        }
    }
}
