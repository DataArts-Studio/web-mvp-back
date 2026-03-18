package com.data_arts_studio.web_mvp_back.milestone.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ArchiveMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.ArchiveMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 아카이브 유스케이스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ArchiveMilestoneService implements ArchiveMilestoneUseCase {
    private final LoadMilestonePort loadMilestonePort;
    private final SaveMilestonePort saveMilestonePort;

    @Override
    /**
     * 마일스톤 아카이브 처리
     *
     * @param command 아카이브 요청 정보
     */
    public void archiveMilestone(ArchiveMilestoneCommand command) {
        // TODO(authz): 인증 도입 후에는 milestoneId 단건 조회 대신 프로젝트/호출자 권한까지 함께 검증할 것.
        Milestone milestone = loadMilestonePort.loadById(new MilestoneId(command.milestoneId()))
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
        milestone.archive();
        saveMilestonePort.updateMilestone(milestone);
    }
}
