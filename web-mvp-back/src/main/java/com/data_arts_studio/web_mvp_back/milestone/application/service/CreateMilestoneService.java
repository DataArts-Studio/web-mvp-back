package com.data_arts_studio.web_mvp_back.milestone.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.CreateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.CreateMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.CreateMilestoneResult;
import com.data_arts_studio.web_mvp_back.milestone.application.validator.CreateMilestoneValidator;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneProgressStatus;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 생성 유스케이스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CreateMilestoneService implements CreateMilestoneUseCase {
    private final CreateMilestoneValidator createMilestoneValidator;
    private final SaveMilestonePort saveMilestonePort;

    @Override
    /**
     * 마일스톤 생성 처리
     *
     * @param command 생성 요청 정보
     * @return 마일스톤 생성 결과
     */
    public CreateMilestoneResult createMilestone(CreateMilestoneCommand command) {
        createMilestoneValidator.validate(command);
        // 생성 시 기본 상태는 planned로 고정
        Milestone milestone = new Milestone(
                MilestoneId.create(),
                new ProjectId(command.projectId()),
                command.name().trim(),
                command.description(),
                command.startDate(),
                command.endDate(),
                MilestoneProgressStatus.PLANNED);
        saveMilestonePort.createMilestone(milestone);
        return CreateMilestoneResult.builder()
                .id(milestone.getId().getId())
                .projectId(milestone.getProjectId().getId())
                .name(milestone.getName())
                .description(milestone.getDescription())
                .status(milestone.getStatus().getDbValue())
                .startDate(milestone.getStartDate())
                .endDate(milestone.getEndDate())
                .createdAt(milestone.getCreatedAt())
                .build();
    }
}
