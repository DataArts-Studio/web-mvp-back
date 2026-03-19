package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.Optional;

import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

/**
 * 마일스톤 로드 포트
 */
public interface LoadMilestonePort {

    // TODO(authz): 프로젝트/사용자 권한 검증을 붙일 때는 projectId를 함께 받는 조회 메서드 추가를 우선 검토할 것.

    /**
     * 활성 마일스톤을 식별자로 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 조회 결과
     */
    Optional<Milestone> loadById(MilestoneId milestoneId);
}
