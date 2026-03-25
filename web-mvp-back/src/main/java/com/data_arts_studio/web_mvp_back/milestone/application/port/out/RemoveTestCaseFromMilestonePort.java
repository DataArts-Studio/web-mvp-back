package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

/**
 * 마일스톤 테스트 케이스 연결 한 건을 해제하는 포트
 */
public interface RemoveTestCaseFromMilestonePort {

    /**
     * 마일스톤과 테스트 케이스의 연결을 제거
     *
     * @param milestoneId 마일스톤 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    void remove(String milestoneId, String testCaseId);
}
