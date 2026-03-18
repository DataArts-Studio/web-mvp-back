package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

/**
 * 마일스톤에 테스트 스위트 한 건을 연결하는 포트
 */
public interface AssignTestSuiteToMilestonePort {

    /**
     * 마일스톤에 테스트 스위트를 연결
     *
     * @param milestoneId 마일스톤 식별자
     * @param testSuiteId 테스트 스위트 식별자
     */
    void assign(String milestoneId, String testSuiteId);
}
