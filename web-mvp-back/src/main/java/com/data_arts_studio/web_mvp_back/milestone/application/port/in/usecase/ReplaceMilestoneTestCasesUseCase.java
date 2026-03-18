package com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ReplaceMilestoneTestCasesCommand;

/**
 * 마일스톤 테스트 범위를 전체 교체하는 유스케이스
 */
public interface ReplaceMilestoneTestCasesUseCase {

    /**
     * 마일스톤 테스트 케이스 집합을 전달된 목록으로 교체
     *
     * @param command 교체 요청 정보
     */
    void replaceMilestoneTestCases(ReplaceMilestoneTestCasesCommand command);
}
