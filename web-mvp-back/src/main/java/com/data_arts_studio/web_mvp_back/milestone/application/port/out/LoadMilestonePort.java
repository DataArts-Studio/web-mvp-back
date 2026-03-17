package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.Optional;

import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

/**
 * 마일스톤 로드 포트
 */
public interface LoadMilestonePort {

    /**
     * 활성 마일스톤을 식별자로 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 조회 결과
     */
    Optional<Milestone> loadById(MilestoneId milestoneId);
}
