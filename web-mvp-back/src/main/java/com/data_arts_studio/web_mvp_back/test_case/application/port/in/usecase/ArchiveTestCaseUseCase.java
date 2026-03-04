package com.data_arts_studio.web_mvp_back.test_case.application.port.in.usecase;

public interface ArchiveTestCaseUseCase {
    /**
     * TestCase를 id로 아카이브 처리
     * @param testCaseId
     */
    void archiveTestCase(String testCaseId);
}
