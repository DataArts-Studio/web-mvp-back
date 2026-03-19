package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

/**
 * 마일스톤 기반 테스트 실행 저장 포트
 */
public interface SaveMilestoneTestRunPort {

    /**
     * 테스트 실행 레코드를 생성
     *
     * @param projectId 프로젝트 식별자
     * @param milestoneId 마일스톤 식별자
     * @param name 테스트 실행 이름
     * @param description 테스트 실행 설명
     * @return 생성된 테스트 실행 식별자
     */
    String createTestRun(String projectId, String milestoneId, String name, String description);
}
