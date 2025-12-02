package com.data_arts_studio.web_mvp_back.test_case.domain;

import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.shared.DomainValidators;
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
        DomainValidators.requireNonNull(id, "TestCaseId는 null일 수 없습니다.");
        DomainValidators.requireNonNull(testSuiteId, "TestSuiteId는 null일 수 없습니다");
        DomainValidators.requireNonEmpty(name, "테스트 케이스 이름은 null 또는 빈 값일 수 없습니다.");
        DomainValidators.normalizeSortOrder(sortOrder);

        // if (id == null) {
        //     throw new IllegalArgumentException("TestCaseId는 null일 수 없습니다.");
        // }
        // if (testSuiteId == null) {
        //     throw new IllegalArgumentException("TestSuiteId는 null일 수 없습니다.");
        // }
        // if (name == null || name.isEmpty()) {
        //     throw new IllegalArgumentException("테스트 케이스 이름은 null 또는 빈 값일 수 없습니다.");
        // }
        // // sortOrder는 NOT NULL, DEFAULT 0, 음수 불가 하여야 한다. 
        // if (sortOrder == null) {
        //     this.sortOrder = 0;
        // } else if (sortOrder < 0) {
        //     throw new IllegalArgumentException("sortOrder는 0 이상이어야 합니다.");
        // } else {
        //     this.sortOrder = sortOrder;
        // }

        this.id = id;
        this.testSuiteId = testSuiteId;
        this.name = name;
        this.status = status;
        this.sortOrder = DomainValidators.normalizeSortOrder(sortOrder);
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
