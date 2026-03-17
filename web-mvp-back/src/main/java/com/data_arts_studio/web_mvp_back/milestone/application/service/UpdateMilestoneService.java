package com.data_arts_studio.web_mvp_back.milestone.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.UpdateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.UpdateMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.UpdateMilestoneResult;
import com.data_arts_studio.web_mvp_back.milestone.application.validator.UpdateMilestoneValidator;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 수정 유스케이스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateMilestoneService implements UpdateMilestoneUseCase {
    private final LoadMilestonePort loadMilestonePort;
    private final SaveMilestonePort saveMilestonePort;
    private final UpdateMilestoneValidator updateMilestoneValidator;

    /**
     * 마일스톤 기본 정보 수정
     *
     * @param command 수정 요청 정보
     * @return 마일스톤 수정 결과
     */
    @Override
    public UpdateMilestoneResult updateMilestone(UpdateMilestoneCommand command) {
        MilestoneId milestoneId = new MilestoneId(command.milestoneId());

        Milestone milestone = loadMilestonePort.loadById(milestoneId)
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
        String projectId = milestone.getProjectId().getId();

        updateMilestoneValidator.validate(command, projectId);
        milestone.rename(command.name().trim());
        milestone.updateDescription(command.description());
        milestone.reschedule(command.startDate(), command.endDate());

        saveMilestonePort.updateMilestone(milestone);

        return UpdateMilestoneResult.builder()
                .id(milestoneId.getId())
                .projectId(projectId)
                .name(milestone.getName())
                .description(milestone.getDescription())
                .status(milestone.getStatus().getDbValue())
                .startDate(milestone.getStartDate())
                .endDate(milestone.getEndDate())
                .updatedAt(milestone.getUpdatedAt())
                .build();
    }
}
