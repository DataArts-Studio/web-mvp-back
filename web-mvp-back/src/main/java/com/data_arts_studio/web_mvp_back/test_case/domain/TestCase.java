package com.data_arts_studio.web_mvp_back.test_case.domain;

import java.time.LocalDateTime;

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
    private String[] tags; // 태그 목록 (smoke, critical-path)
    private final String steps; // Steps 본문 전체 갱신용
    private String expectedResult;// Expected Result 본문 전체 갱신용
    private int estimateMinutes; // 예상 소요 시간 (분 단위)
    private int sortOrder; // 테스트 스위트 내 노출/정렬 순서
    private TestCasePriority priority; // 테스트 케이스 우선순위


    // 테스트 케이스 신규 생성용 생성자
    public TestCase(TestCaseId id, ProjectId projectId, TestSuiteId testSuiteId, String name, String steps, String expectedResult, int estimateMinutes, Integer sortOrder) {
        super();
        // 도메인 모델 검증 유틸 사용
        this.id = id;
        this.projectId = projectId;
        this.testSuiteId = testSuiteId;
        this.name = name;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.estimateMinutes = estimateMinutes;
        this.sortOrder = sortOrder;
    }

    // 복원용 생성자
    public TestCase(TestCaseId id, ProjectId projectId, TestSuiteId testSuiteId, String caseKey, String name, String testType, String[] tags, String steps, String expectedResult, int estimateMinutes, int sortOrder, TestCasePriority priority, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.projectId = projectId;
        this.testSuiteId = testSuiteId;
        this.caseKey = caseKey;
        this.name = name;
        this.testType = testType;
        this.tags = tags;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.estimateMinutes = estimateMinutes;
        this.sortOrder = sortOrder;
        this.priority = priority;
        restoreAuditFields(createdAt, updatedAt, deletedAt);
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
    public String[] getTags() {
        return tags;
    }
    public String getSteps() {
        return steps;
    }
    public String getExpectedResult() {
        return expectedResult;
    }
    public int getEstimateMinutes() {
        return estimateMinutes;
    }
    public int getSortOrder() {
        return sortOrder;
    }
    public TestCasePriority getPriority() {
        return priority;
    } 


}
