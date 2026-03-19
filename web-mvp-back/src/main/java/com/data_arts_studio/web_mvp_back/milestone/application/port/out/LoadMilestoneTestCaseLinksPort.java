package com.data_arts_studio.web_mvp_back.milestone.application.port.out;

import java.util.List;

/**
 * 마일스톤에 연결된 테스트 케이스 식별자 목록을 로드하는 포트
 */
public interface LoadMilestoneTestCaseLinksPort {

    /**
     * 마일스톤에 연결된 테스트 케이스 식별자 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 케이스 식별자 목록
     */
    List<String> loadTestCaseIdsByMilestone(String milestoneId);
}
