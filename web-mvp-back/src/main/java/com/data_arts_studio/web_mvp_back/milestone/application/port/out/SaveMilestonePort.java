package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;

/**
 * 마일스톤 저장 포트
 */
public interface SaveMilestonePort {

    /**
     * 새 마일스톤을 저장
     *
     * @param milestone 저장할 마일스톤
     */
    void createMilestone(Milestone milestone);

    /**
     * 기존 마일스톤을 갱신 저장
     *
     * @param milestone 갱신할 마일스톤
     */
    void updateMilestone(Milestone milestone);
}
