package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;

/**
 * 마일스톤 테스트 스위트 범위를 전체 교체하는 포트
 */
public interface ReplaceMilestoneTestSuitesPort {

    /**
     * 마일스톤에 연결된 테스트 스위트 집합을 교체
     *
     * @param milestoneId 마일스톤 식별자
     * @param testSuiteIds 최종 테스트 스위트 식별자 목록
     */
    void replace(String milestoneId, List<String> testSuiteIds);
}
