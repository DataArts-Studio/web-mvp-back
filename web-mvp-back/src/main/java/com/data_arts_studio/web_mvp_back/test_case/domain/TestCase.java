package com.data_arts_studio.web_mvp_back.test_case.domain;

import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

public class TestCase extends BaseEntity {
    private final TestCaseId id; // 테스트 케이스 식별자
    private final TestSuiteId testSuiteId; // 연관 식별자 (TestSuiteId)
    private String name; // 테스트 케이스 이름
    private TestCaseStatus status; // 테스트 케이스 상태
    private int sortOrder; // 테스트 스위트 내 노출/정렬 순서

    public TestCase(TestCaseId id, TestSuiteId testSuiteId, String name, TestCaseStatus status, Integer sortOrder) {
        super();
        // 도메인 모델 검증 유틸 사용
        this.id = id;
        this.testSuiteId = testSuiteId;
        this.name = name;
        this.status = status;
        this.sortOrder = sortOrder;
    }

    public TestCaseId getId() {
        return id;
    }

    public TestSuiteId getTestSuiteId() {
        return testSuiteId;
    }

    public String getName() {
        return name;
    }

    public TestCaseStatus getStatus() {
        return status;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }

    public void updateStatus(TestCaseStatus newStatus) {
        this.status = newStatus;
        markUpdated();
    }

    public void updateSortOrder(int newSortOrder) {
        this.sortOrder = newSortOrder;
        markUpdated();
    }

}
