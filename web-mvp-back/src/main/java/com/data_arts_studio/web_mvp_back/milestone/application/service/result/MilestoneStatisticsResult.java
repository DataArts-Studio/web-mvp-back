package com.data_arts_studio.web_mvp_back.milestone.application.service.result;

/**
 * 마일스톤 진행률과 테스트 실행 통계를 담는 조회 결과
 *
 * @param milestoneId 마일스톤 식별자
 * @param totalCaseCount 연결된 전체 테스트 케이스 수
 * @param completedCaseCount 완료된 테스트 케이스 수
 * @param progressPercent 진행률
 * @param testRunCount 테스트 실행 수
 */
public record MilestoneStatisticsResult(
        String milestoneId,
        int totalCaseCount,
        int completedCaseCount,
        int progressPercent,
        int testRunCount) {

    public static MilestoneStatisticsResult empty(String milestoneId) {
        return new MilestoneStatisticsResult(milestoneId, 0, 0, 0, 0);
    }
}
