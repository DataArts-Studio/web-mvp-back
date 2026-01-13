package com.data_arts_studio.web_mvp_back.test_case.domain;


import java.time.OffsetDateTime;
import java.util.List;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

public class TestCase extends BaseEntity {
    private final TestCaseId id; // 테스트 케이스 식별자
    private final ProjectId projectId; // 연관 프로젝트 식별자

    private TestSuiteId testSuiteId; // 연관 테스트 스위트 식별자 

    private String caseKey; // TC-1001 같은 표시용 키
    private String name; // 테스트 케이스 이름
    private String testType; // 테스트 유형 (기능 테스트, 회귀 테스트 등)
    private List<String> tags; // 태그 목록 (smoke, critical-path)
    private String preCondition; // 테스트 실행 전제조건
    private String steps; // Steps 본문 전체 갱신용
    private String expectedResult;// Expected Result 본문 전체 갱신용
    private int sortOrder; // 테스트 스위트 내 노출/정렬 순서

    private ResultStatus resultStatus; // 테스트 케이스 결과 상태 

    // 테스트 케이스 신규 생성용 생성자
    public TestCase(TestCaseId id, ProjectId projectId, TestSuiteId testSuiteId, String caseKey, String name, String testType, List<String> tags, String preCondition, String steps, String expectedResult, int sortOrder, ResultStatus resultStatus) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.testSuiteId = testSuiteId;
        this.caseKey = caseKey;
        this.name = name;
        this.testType = testType;
        this.tags = tags;
        this.preCondition = preCondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.sortOrder = sortOrder;
        this.resultStatus = resultStatus;
    }

    // 복원용 생성자
    public TestCase(TestCaseId id, ProjectId projectId, TestSuiteId testSuiteId, String caseKey, String name, String testType, List<String> tags, String preCondition,String steps, String expectedResult, int sortOrder, ResultStatus resultStatus, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime archivedAt) {
        this.id = id;
        this.projectId = projectId;
        this.testSuiteId = testSuiteId;
        this.caseKey = caseKey;
        this.name = name;
        this.testType = testType;
        this.tags = tags;
        this.preCondition = preCondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.sortOrder = sortOrder;
        this.resultStatus = resultStatus;
        restoreAuditFields(createdAt, updatedAt, archivedAt);
    }

    // 이름 변경
    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }
    // 테스트 스위트 변경
    public void changeSuite(TestSuiteId newSuiteId) {
        this.testSuiteId = newSuiteId;
        markUpdated();
    }

    // 예상 소요 시간 변경
    public void updateSortOrder(int newSortOrder) {
        this.sortOrder = newSortOrder;
        markUpdated();
    }

    // Getter
    public TestCaseId getId() {
        return id;
    }
    public ProjectId getProjectId() {
        return projectId;
    }
    public TestSuiteId getTestSuiteId() {
        return testSuiteId;
    }
    public String getCaseKey() {
        return caseKey;
    }
    public String getName() {
        return name;
    }
    public String getTestType() {
        return testType;
    }
    public List<String> getTags() {
        return tags;
    }
    public String getPreCondition() {
        return preCondition;
    }
    public String getSteps() {
        return steps;
    }
    public String getExpectedResult() {
        return expectedResult;
    }
    public int getSortOrder() {
        return sortOrder;
    }
    public ResultStatus getResultStatus() {
        return resultStatus;
    }
}