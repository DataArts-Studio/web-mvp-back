package com.data_arts_studio.web_mvp_back.test_case.application.port.in.usecase;

import com.data_arts_studio.web_mvp_back.test_case.application.port.in.command.ArchiveTestCaseCommand;

/**
 * 테스트 케이스 아카이브 처리 인바운드 유스케이스.
 *
 * 웹 어댑터 등 입력 계층에서 전달한 아카이브 요청 커맨드를 받아 지정된 테스트 케이스를 아카이브 처리
 */
public interface ArchiveTestCaseUseCase {

    /**
     * 특정 프로젝트 범위의 테스트 케이스를 아카이브 처리
     *
     * @param command 아카이브 대상 프로젝트/테스트 케이스 식별자 커맨드
     */
    void archiveTestCase(ArchiveTestCaseCommand command);
}
