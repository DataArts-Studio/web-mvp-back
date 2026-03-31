package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;

/**
 * 마일스톤 상세 조회 결과
 *
 * @param id 마일스톤 식별자
 * @param projectId 마일스톤이 속한 프로젝트 식별자
 * @param name 마일스톤 이름
 * @param description 마일스톤 설명
 * @param status 마일스톤 진행 상태
 * @param startDate 마일스톤 시작 일시
 * @param endDate 마일스톤 종료 일시
 * @param progressPercent 마일스톤 진행률
 * @param completedCaseCount 완료된 테스트 케이스 수
 * @param totalCaseCount 연결된 테스트 케이스 개수
 * @param testRunCount 테스트 실행 횟수
 * @param createdAt 마일스톤 생성 일시
 * @param updatedAt 마일스톤 마지막 수정 일시
 * @param testCases 포함된 테스트 케이스 리스트
 * @param testSuites 포함된 테스트 스위트 리스트
 * @param testRuns 테스트 실행 이력 리스트
 */
@Builder
public record GetMilestoneDetailResult(
        String id,
        String projectId,
        String name,
        String description,
        String status,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        int progressPercent,
        int completedCaseCount,
        int totalCaseCount,
        int testRunCount,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<GetMilestoneTestCaseItemResult> testCases,
        List<GetMilestoneTestSuiteItemResult> testSuites,
        List<GetMilestoneTestRunItemResult> testRuns) {
}
