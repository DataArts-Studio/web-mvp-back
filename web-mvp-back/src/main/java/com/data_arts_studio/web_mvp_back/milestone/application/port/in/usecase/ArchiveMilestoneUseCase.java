package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ArchiveMilestoneCommand;

/**
 * 마일스톤 아카이브 유스케이스
 */
public interface ArchiveMilestoneUseCase {

    /**
     * 마일스톤 아카이브 처리
     *
     * @param command 아카이브 요청 정보
     */
    void archiveMilestone(ArchiveMilestoneCommand command);
}
