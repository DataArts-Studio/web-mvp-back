package com.data_arts_studio.web_mvp_back.test_suite.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.AssignTestCaseToSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.RemoveTestCaseFromSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.command.ReplaceSuiteTestCasesCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.AssignTestCaseToSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.QuerySuiteTestCaseLinksUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.RemoveTestCaseFromSuiteUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.usecase.ReplaceSuiteTestCasesUseCase;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.AssignTestCaseToSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadSuiteTestCaseLinksPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.RemoveTestCaseFromSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.ReplaceSuiteTestCasesPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetSuiteTestCaseLinksResult;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 스위트와 테스트 케이스 간 링크 관리 유스케이스 구현체
 * 링크 단건 추가, 전체 교체, 단건 해제, 연결 목록 조회를 아웃 포트에 위임
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SuiteTestCaseLinkService implements AssignTestCaseToSuiteUseCase,
        RemoveTestCaseFromSuiteUseCase,
        ReplaceSuiteTestCasesUseCase,
        QuerySuiteTestCaseLinksUseCase {

    private final AssignTestCaseToSuitePort assignTestCaseToSuitePort;
    private final RemoveTestCaseFromSuitePort removeTestCaseFromSuitePort;
    private final ReplaceSuiteTestCasesPort replaceSuiteTestCasesPort;
    private final LoadSuiteTestCaseLinksPort loadSuiteTestCaseLinksPort;

    @Override
    /**
     * 테스트 스위트에 테스트 케이스를 1건 연결
     *
     * @param command 프로젝트/스위트/테스트케이스 식별자를 담은 커맨드
     */
    public void assignTestCaseToSuite(AssignTestCaseToSuiteCommand command) {
        assignTestCaseToSuitePort.assign(command.projectId(), command.suiteId(), command.testCaseId());
    }

    @Override
    /**
     * 테스트 스위트에서 테스트 케이스 연결 1건을 제거
     *
     * @param command 프로젝트/스위트/테스트케이스 식별자를 담은 커맨드
     */
    public void removeTestCaseFromSuite(RemoveTestCaseFromSuiteCommand command) {
        removeTestCaseFromSuitePort.remove(command.projectId(), command.suiteId(), command.testCaseId());
    }

    @Override
    /**
     * 테스트 스위트에 연결된 테스트 케이스 구성을 전달된 목록으로 전체 교체
     *
     * @param command 프로젝트/스위트 식별자와 최종 테스트 케이스 목록을 담은 커맨드
     */
    public void replaceSuiteTestCases(ReplaceSuiteTestCasesCommand command) {
        replaceSuiteTestCasesPort.replace(command.projectId(), command.suiteId(), command.testCaseIds());
    }

    @Override
    @Transactional(readOnly = true)
    /**
     * 특정 테스트 스위트에 연결된 테스트 케이스 ID 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 연결된 테스트 케이스 목록 조회 결과
     */
    public GetSuiteTestCaseLinksResult getSuiteTestCaseLinks(String projectId, String suiteId) {
        List<String> testCaseIds = loadSuiteTestCaseLinksPort.loadTestCaseIdsBySuite(projectId, suiteId);
        return GetSuiteTestCaseLinksResult.builder()
                .suiteId(suiteId)
                .testCaseIds(testCaseIds)
                .build();
    }
}
