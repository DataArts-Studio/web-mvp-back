package com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response;

import java.time.OffsetDateTime;

/**
 * 테스트 케이스 목록 조회 응답 모델
 * @param id          테스트 케이스 ID
 * @param projectId   소속 프로젝트 ID
 * @param name        테스트 케이스 이름
 * @param latestModifiedAt 생성/수정 기준 마지막 변경 시간
 */
public record TestCaseListItemResponse(
    // displayId는 프론트에서 caseKey로 매핑하여 보여줄 것으로 예상하며, 여기서는 id만 포함
    String id,
    String projectId,
    String name,
    OffsetDateTime latestModifiedAt
) {
}
