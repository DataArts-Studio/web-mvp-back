package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 마일스톤 상세 응답 DTO
 *
 * @param id                 마일스톤 고유 식별자
 * @param projectId          마일스톤이 속한 프로젝트 식별자
 * @param name               마일스톤 이름
 * @param description        마일스톤 상세 설명
 * @param status             마일스톤 진행 상태 
 * @param startDate          마일스톤 시작 일시
 * @param endDate            마일스톤 종료(목표) 일시
 * @param progressPercent    전체 진행률 (퍼센트 단위, 0~100)
 * @param completedCaseCount 완료된(성공/실패 처리된) 테스트 케이스 수
 * @param totalCaseCount     연결된 전체 테스트 케이스 수
 * @param testRunCount       해당 마일스톤 내에서 실행된 테스트 런(Test Run) 횟수/개수
 *                           TODO(test-run): 테스트 런 상세 조회 구현 후 실제 집계 기준과 함께 고도화할 것
 * @param createdAt          마일스톤 생성 일시
 * @param updatedAt          마일스톤 마지막 수정 일시
 * @param testCases          마일스톤에 포함된 테스트 케이스 목록
 * @param testSuites         마일스톤에 포함된 테스트 스위트 목록
 * @param testRuns           마일스톤 테스트 실행 이력 목록
 *                           TODO(test-run): 테스트 런 조회 기능 구현 전까지는 빈 목록으로 응답
 */
public record GetMilestoneDetailResponse(
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
        List<GetMilestoneTestCaseItemResponse> testCases,
        List<GetMilestoneTestSuiteItemResponse> testSuites,
        List<GetMilestoneTestRunItemResponse> testRuns) {
}
