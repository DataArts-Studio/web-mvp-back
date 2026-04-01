package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;
import java.util.Optional;

import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneDetailResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetProjectMilestoneItemResult;

/**
 * 마일스톤 조회 전용 포트
 */
public interface MilestoneQueryPort {

    /**
     * 프로젝트에 속한 활성 마일스톤 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 프로젝트 마일스톤 목록
     */
    List<GetProjectMilestoneItemResult> findAllByProject(String projectId);

    /**
     * 마일스톤 상세 정보와 통계를 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 상세 정보
     */
    Optional<GetMilestoneDetailResult> findDetail(String milestoneId);
}
