package com.data_arts_studio.web_mvp_back.test_run.domain;

import java.time.OffsetDateTime;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.shared.LifecycleStatus;

/**
 * 테스트 런 도메인 모델
 *
 * 프로젝트에 속한 테스트 실행 단위를 나타내는 집계 루트.
 * 이름·설명·진행 상태·공유 정보를 보유하며, 상태 변경과 공유 갱신 등의
 * 도메인 행위를 캡슐화
 */
public class TestRun extends BaseEntity {
    // 테스트 런 식별자
    private final TestRunId id;

    // 테스트 런이 속한 프로젝트 식별자
    private final ProjectId projectId;

    // 테스트 런 이름
    private String name;

    // 테스트 런 설명
    private String description;

    // 테스트 런 진행 상태
    private TestRunStatus status;

    // 외부 공유용 토큰
    private String shareToken;

    // 공유 링크 만료 시각
    private OffsetDateTime shareExpiresAt;

    // 공유 페이지에 표시할 AI 요약
    private String shareAiSummary;

    /**
     * 신규 테스트 런을 생성
     *
     * @param id          테스트 런 식별자
     * @param projectId   프로젝트 식별자
     * @param name        테스트 런 이름
     * @param description 테스트 런 설명
     * @param status      테스트 런 상태
     */
    public TestRun(TestRunId id,
                   ProjectId projectId,
                   String name,
                   String description,
                   TestRunStatus status) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    /**
     * 저장된 테스트 런 상태를 도메인 모델로 복원
     *
     * @param id             테스트 런 식별자
     * @param projectId      프로젝트 식별자
     * @param name           테스트 런 이름
     * @param description    테스트 런 설명
     * @param status         테스트 런 상태
     * @param lifecycleStatus 라이프사이클 상태
     * @param createdAt      생성 시각
     * @param updatedAt      수정 시각
     * @param archivedAt     아카이브 시각
     * @param shareToken     공유 토큰
     * @param shareExpiresAt 공유 만료 시각
     * @param shareAiSummary 공유용 AI 요약
     */
    public TestRun(TestRunId id,
                   ProjectId projectId,
                   String name,
                   String description,
                   TestRunStatus status,
                   LifecycleStatus lifecycleStatus,
                   OffsetDateTime createdAt,
                   OffsetDateTime updatedAt,
                   OffsetDateTime archivedAt,
                   String shareToken,
                   OffsetDateTime shareExpiresAt,
                   String shareAiSummary) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.lifecycleStatus = lifecycleStatus;
        this.shareToken = shareToken;
        this.shareExpiresAt = shareExpiresAt;
        this.shareAiSummary = shareAiSummary;
        restoreAuditFields(createdAt, updatedAt, archivedAt);
    }

    public TestRunId getId() { return id; }

    public ProjectId getProjectId() { return projectId; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public TestRunStatus getStatus() { return status; }

    public String getShareToken() { return shareToken; }

    public OffsetDateTime getShareExpiresAt() { return shareExpiresAt; }

    public String getShareAiSummary() { return shareAiSummary; }

    /**
     * 테스트 런 이름을 변경
     *
     * @param newName 새 이름
     */
    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }

    /**
     * 테스트 런 설명을 변경
     *
     * @param newDescription 새 설명
     */
    public void updateDescription(String newDescription) {
        this.description = newDescription;
        markUpdated();
    }

    /**
     * 테스트 런 상태를 변경
     *
     * @param newStatus 새 상태
     */
    public void updateStatus(TestRunStatus newStatus) {
        this.status = newStatus;
        markUpdated();
    }

    /**
     * 공유 토큰·만료 시각·AI 요약을 한 번에 갱신
     *
     * @param newShareToken     새 공유 토큰
     * @param newShareExpiresAt 새 공유 만료 시각
     * @param newShareAiSummary 새 공유용 AI 요약
     */
    public void updateShareInfo(String newShareToken, OffsetDateTime newShareExpiresAt, String newShareAiSummary) {
        this.shareToken = newShareToken;
        this.shareExpiresAt = newShareExpiresAt;
        this.shareAiSummary = newShareAiSummary;
        markUpdated();
    }

    /**
     * 테스트 런을 소프트 삭제(아카이브) 처리
     *
     * <p>물리적으로 레코드를 삭제하지 않고 {@code archived_at} 타임스탬프를 기록하여
     * 이후 조회 대상에서 제외.</p>
     */
    public void archive() {
        markArchived();
    }
}
