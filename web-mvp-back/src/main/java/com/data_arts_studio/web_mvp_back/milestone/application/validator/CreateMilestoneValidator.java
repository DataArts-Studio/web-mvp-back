package com.data_arts_studio.web_mvp_back.milestone.application.validator;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.CreateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.CheckMilestoneNamePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.CheckProjectExistsPort;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 생성 요청의 유효성을 검증
 */
@Component
@RequiredArgsConstructor
public class CreateMilestoneValidator {
    private final CheckProjectExistsPort checkProjectExistsPort;
    private final CheckMilestoneNamePort checkMilestoneNamePort;

    /**
     * 마일스톤 생성 요청을 검증
     *
     * @param command 생성 요청 정보
     */
    public void validate(CreateMilestoneCommand command) {
        validateProjectExists(command.projectId());
        validateName(command.name());
        validateDescription(command.description());
        validateDateRange(command.startDate(), command.endDate());
        validateNameDuplicated(command.projectId(), command.name().trim());
    }

    // 프로젝트 존재 여부 검증
    private void validateProjectExists(String projectId) {
        if (!checkProjectExistsPort.existsById(projectId)) {
            throw new MilestoneBusinessException(MilestoneErrorCode.PROJECT_NOT_FOUND);
        }
    }

    // 이름 필수 여부와 최대 길이 검증
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NAME_EMPTY);
        }
        if (name.trim().length() > 50) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NAME_TOO_LONG);
        }
    }

    // 설명 최대 길이 검증
    private void validateDescription(String description) {
        if (description != null && description.length() > 500) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_DESCRIPTION_TOO_LONG);
        }
    }

    // 시작일과 종료일 순서 검증
    private void validateDateRange(OffsetDateTime startDate, OffsetDateTime endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new MilestoneBusinessException(MilestoneErrorCode.INVALID_DATE_RANGE);
        }
    }

    // 같은 프로젝트 내 이름 중복 여부 검증
    private void validateNameDuplicated(String projectId, String name) {
        if (checkMilestoneNamePort.isMilestoneNameDuplicated(projectId, name)) {
            throw new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NAME_DUPLICATED);
        }
    }
}
