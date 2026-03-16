package com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.ReplaceSuiteTestCasesCommand;

/**
 * 테스트 스위트에 연결된 테스트 케이스 구성을 전체 교체하는 유스케이스.
 */
public interface ReplaceSuiteTestCasesUseCase {

    /**
     * 특정 스위트의 테스트 케이스 링크를 전달된 목록으로 전체 교체
     *
     * @param command 프로젝트/스위트 식별자와 최종 테스트 케이스 목록을 담은 커맨드
     */
    void replaceSuiteTestCases(ReplaceSuiteTestCasesCommand command);
}
