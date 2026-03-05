package com.data_arts_studio.web_mvp_back.test_suite.application.service.result;

import java.time.OffsetDateTime;
import lombok.Builder;

/**
 * 프로젝트 내 테스트 스위트 목록 조회 시 사용되는 개별 항목 DTO
 *
 * 프로젝트에 속한 여러 테스트 스위트를 목록 형태로 조회할 때 각 스위트의 요약 정보를 표현하기 위한 조회 모델(Read Model)
 * UI에서는 리스트 형태로 표시되며, 스위트의 기본 정보와 실행 관련 요약 정보를 포함함
 *
 * 마일스톤 또는 실행 기록이 존재하지 않을 수 있으며, 해당 값은 null 또는 0으로 반환됨
 *
 * @param suiteId        테스트 스위트 식별자
 * @param name           테스트 스위트 이름
 * @param type           테스트 스위트 유형 (예: 기본, 기능별, 시나리오 등)
 * @param testCaseCount  스위트에 포함된 테스트 케이스 수
 * @param milestoneName  연결된 마일스톤 이름
 * @param lastExecutedAt 가장 최근 실행 시각
 * @param executionCount 테스트 실행 총 횟수
 */
@Builder
public record GetProjectTestSuiteItemResult(
    String suiteId,
    String name,
    String type,
    int testCaseCount,
    String milestoneName,
    OffsetDateTime lastExecutedAt,
    int executionCount
) {
}