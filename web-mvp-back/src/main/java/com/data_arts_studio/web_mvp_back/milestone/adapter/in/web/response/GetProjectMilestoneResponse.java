package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.util.List;

/**
 * 프로젝트 마일스톤 목록 응답 DTO
 *
 * @param items 프로젝트에 속한 마일스톤 목록
 */
public record GetProjectMilestoneResponse(
    List<GetProjectMilestoneItemResponse> items) {
}
