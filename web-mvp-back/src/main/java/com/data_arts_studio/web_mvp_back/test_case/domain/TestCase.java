package com.data_arts_studio.web_mvp_back.test_case.domain;

import java.time.OffsetDateTime;
import java.util.List;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.shared.LifecycleStatus;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseBusinessException;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseErrorCode;
import com.data_arts_studio.web_mvp_back.test_suite.domain.TestSuiteId;

public class TestCase extends BaseEntity {
    private final TestCaseId id; // 테스트 케이스 식별자
    private final ProjectId projectId; // 연관 식별자 (ProjectId)
    private TestSuiteId testSuiteId; // 연관 식별자 (TestSuiteId)

    private String caseKey; // TC-1001 같은 표시용 키
    private String name; // 테스트 케이스 이름

    private String testType;// 테스트 케이스 분류 (기능, 동작, 회귀, 스모크)
    private List<String> tags; // 태그 목록 (예: smoke, critical-path)

    private String preCondition; // 테스트 전체 실행 조건 
    private String steps; // step 본분
    private String expectedResult; // 기대 결과

    private int sortOrder; // 정렬 순서
    private ResultStatus resultStatus; // 테스트 케이스 결과 상태

    // 테스트 케이스를 생성할 때 사용하는 생성자
    public TestCase(TestCaseId id,
                    ProjectId projectId,
                    TestSuiteId testSuiteId,
                    String caseKey,
                    String name,
                    String testType,
                    List<String> tags,
                    String preCondition,
                    String steps,
                    String expectedResult,
                    int sortOrder) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.testSuiteId = testSuiteId;
        this.caseKey = caseKey;
        this.name = validateName(name);
        this.testType = testType;
        this.tags = tags;
        this.preCondition = preCondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.sortOrder = sortOrder;
        this.resultStatus = ResultStatus.UNTESTED; // 신규 생성은 항상 미실행 상태
    }
    // 이미 존재하는 데이터를 객체로 변환 시에 사용하는 생성자
    // Persistence Adapter에서 사용
    public TestCase(TestCaseId id,
                    ProjectId projectId,
                    TestSuiteId testSuiteId,
                    String caseKey,
                    String name,
                    String testType,
                    List<String> tags,
                    String preCondition,
                    String steps,
                    String expectedResult,
                    int sortOrder,
                    ResultStatus resultStatus,
                    OffsetDateTime createdAt,
                    OffsetDateTime updatedAt,
                    OffsetDateTime archivedAt,
                    LifecycleStatus lifecycleStatus
    ) {
        this.id = id;
        this.projectId = projectId;
        this.testSuiteId = testSuiteId;
        this.caseKey = caseKey;
        this.name = validateName(name);
        this.testType = testType;
        this.tags = tags;
        this.preCondition = preCondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.sortOrder = sortOrder;
        this.resultStatus = resultStatus;
        // BaseEntity의 감사 필드 복원 메서드 호출
        restoreAuditFields(createdAt, updatedAt, archivedAt);
        this.lifecycleStatus = lifecycleStatus;
    }

    // Getter 메서드
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

    // 테스트 케이스 세부 정보 업데이트 
    public void updateDetails(TestSuiteId newSuiteId,
                              String name,
                              String testType,
                              List<String> tags,
                              String preCondition,
                              String steps,
                              String expectedResult) {
        this.testSuiteId = newSuiteId;
        this.name = validateName(name);
        this.testType = testType;
        this.tags = tags;
        this.preCondition = preCondition;
        this.steps = steps;
        this.expectedResult = expectedResult;
        markUpdated();
    }
    // 테스트 케이스 결과 상태 업데이트 
    public void updateResultStatus(ResultStatus newStatus) {
        this.resultStatus = newStatus;
        markUpdated();
    }

    // 정렬 순서 변경 
    public void changeSortOrder(int newSortOrder) {
        this.sortOrder = newSortOrder;
        markUpdated();
    } 

    // 테스트 케이스 아카이브
    public void archive() {
        this.markArchived();
    }
    
    // 어떤 경로로 들어오던지 이름 검증 
    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new TestCaseBusinessException(TestCaseErrorCode.TESTCASE_NAME_EMPTY_VALUE);
        }
        return name;
    }
    
}
