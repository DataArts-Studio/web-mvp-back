package com.data_arts_studio.web_mvp_back.test_suite.application.service.result;

import java.time.OffsetDateTime;
import lombok.Builder;

/**
 * 테스트 스위트 상세 조회 결과 DTO
 *
 * 특정 프로젝트에 속한 테스트 스위트의 상세 정보를 조회할 때 에 반환되는 응답 모델
 * 화면 구성에 필요한 데이터를 집계하여 전달하기 위한 조회 전용(Read Model) 객체
 * 마일스톤 또는 실행 기록이 존재하지 않을 수 있으며, 해당 값은 기본값(0 또는 null)으로 반환됨
 *
 * @param suiteId        테스트 스위트 식별자
 * @param name           테스트 스위트 이름
 * @param description    테스트 스위트 설명
 * @param createdAt      테스트 스위트 생성 시각
 * @param testCaseCount  스위트에 포함된 테스트 케이스 수
 * @param executionCount 해당 스위트가 실행된 총 횟수
 * @param lastPassRate   가장 최근 실행 기준 테스트 통과율 (0~100)
 */
@Builder
public record GetTestSuiteDetailResult(
    String suiteId,
    String name,
    String description,
    OffsetDateTime createdAt,
    int testCaseCount,
    int executionCount,
    double lastPassRate
) {
}