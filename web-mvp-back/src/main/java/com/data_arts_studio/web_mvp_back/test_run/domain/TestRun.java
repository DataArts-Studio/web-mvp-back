package com.data_arts_studio.web_mvp_back.test_run.domain;

import java.time.LocalDateTime;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;

public class TestRun extends BaseEntity {
    // 테스트 런 도메인 모델 스켈레톤
    private final TestRunId id; // 테스트 런 식별자 
    private final TestCaseId testCaseId; // 연관된 테스트 케이스 식별자
    private final MilestoneId milestoneId; // 연관된 마일스톤 식별자
    private TestRunStatus status; // 테스트 런 상태
    private LocalDateTime executedAt; // 테스트 실행 시각

    public TestRun(TestRunId id, TestCaseId testCaseId, MilestoneId milestoneId, TestRunStatus status, LocalDateTime executedAt) {
        super();
        if (id == null) {
            throw new IllegalArgumentException("TestRunId는 null일 수 없습니다.");
        }
        if (testCaseId == null) {
            throw new IllegalArgumentException("TestCaseId는 null일 수 없습니다.");
        }
        if (milestoneId == null) {
            throw new IllegalArgumentException("MilestoneId는 null일 수 없습니다.");
        }
        if (executedAt == null) {
            throw new IllegalArgumentException("executedAt는 null일 수 없습니다.");
        }
        this.id = id;
        this.testCaseId = testCaseId;
        this.milestoneId = milestoneId;
        this.status = status;
        this.executedAt = executedAt;
    }

    public TestRunId getId() {
        return id;
    }

    public TestCaseId getTestCaseId() {
        return testCaseId;
    }

    public MilestoneId getMilestoneId() {
        return milestoneId;
    }

    public TestRunStatus getStatus() {
        return status;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void updateStatus(TestRunStatus newStatus) {
        this.status = newStatus;
        markUpdated();
    }

    public void updateExecutedAt(LocalDateTime newExecutedAt) {
        this.executedAt = newExecutedAt;
        markUpdated();
    }
}
