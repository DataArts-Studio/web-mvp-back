package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.util.List;

import lombok.Builder;

/**
 * 프로젝트 마일스톤 목록 조회 결과
 *
 * @param items 프로젝트에 속한 마일스톤 목록
 */
@Builder
public record GetProjectMilestoneResult(
    List<GetProjectMilestoneItemResult> items) {
}
