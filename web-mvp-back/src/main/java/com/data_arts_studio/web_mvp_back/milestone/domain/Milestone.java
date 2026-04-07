package com.data_arts_studio.web_mvp_back.milestone.domain;

import java.time.OffsetDateTime;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;

/**
 * 마일스톤 도메인 모델
 *
 * 프로젝트 내 테스트 활동의 범위와 일정을 관리하는 집계 루트.
 * 이름·설명·기간·진행 상태를 보유하며, 상태 변경과 일정 재조정 등의
 * 도메인 행위를 캡슐화
 */
public class Milestone extends BaseEntity {
    // 마일스톤 식별자
    private final MilestoneId id;

    // 마일스톤이 속한 프로젝트 식별자 
    private final ProjectId projectId;

    // 마일스톤 이름
    private String name;

    // 마일스톤 설명
    private String description;

    // 마일스톤 시작일
    private OffsetDateTime startDate;

    // 마일스톤 종료일
    private OffsetDateTime endDate;

    // 마일스톤 진행 상태
    private MilestoneProgressStatus milestoneProgressStatus;

    /**
     * 마일스톤 도메인 객체를 생성
     *
     * @param id                      마일스톤 식별자
     * @param projectId               소속 프로젝트 식별자
     * @param name                    마일스톤 이름
     * @param description             마일스톤 설명
     * @param startDate               시작일
     * @param endDate                 종료일
     * @param milestoneProgressStatus 초기 진행 상태
     */
    public Milestone(MilestoneId id,
                     ProjectId projectId,
                     String name,
                     String description,
                     OffsetDateTime startDate,
                     OffsetDateTime endDate,
                     MilestoneProgressStatus milestoneProgressStatus) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.milestoneProgressStatus = milestoneProgressStatus;
    }

    public MilestoneId getId() { return id; }

    public ProjectId getProjectId() { return projectId; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public OffsetDateTime getStartDate() { return startDate; }

    public OffsetDateTime getEndDate() { return endDate; }

    public MilestoneProgressStatus getStatus() { return milestoneProgressStatus; }


    /**
     * 마일스톤 이름을 변경
     *
     * @param newName 새 이름
     */
    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }

    /**
     * 마일스톤 설명을 변경
     *
     * @param newDescription 새 설명
     */
    public void updateDescription(String newDescription) {
        this.description = newDescription;
        markUpdated();
    }

    /**
     * 마일스톤 시작일과 종료일을 수정
     *
     * @param newStartDate 새 시작일
     * @param newEndDate   새 종료일
     */
    public void reschedule(OffsetDateTime newStartDate, OffsetDateTime newEndDate) {
        this.startDate = newStartDate;
        this.endDate = newEndDate;
        markUpdated();
    }

    /**
     * 마일스톤 상태를 {@code PLANNED}로 되돌린다.
     */
    public void markPlanned() {
        updateStatus(MilestoneProgressStatus.PLANNED);
    }

    /**
     * 마일스톤 상태를 {@code IN_PROGRESS}로 변경
     */
    public void markInProgress() {
        updateStatus(MilestoneProgressStatus.IN_PROGRESS);
    }

    /**
     * 마일스톤 상태를 {@code DONE}으로 변경
     */
    public void markCompleted() {
        updateStatus(MilestoneProgressStatus.DONE);
    }

    /**
     * 마일스톤 진행 상태를 임의 값으로 변경
     *
     * <p>{@link #markPlanned()}, {@link #markInProgress()}, {@link #markCompleted()}의
     * 공통 구현으로, 외부에서 직접 호출하기보다 의도가 명확한 전용 메서드 사용을 권장한다.</p>
     *
     * @param newStatus 새 진행 상태
     */
    public void updateStatus(MilestoneProgressStatus newStatus) {
        this.milestoneProgressStatus = newStatus;
        markUpdated();
    }

    /**
     * 마일스톤을 소프트 삭제(아카이브) 처리
     *
     * <p>물리적으로 레코드를 삭제하지 않고 {@code archived_at} 타임스탬프를 기록하여
     * 이후 조회 대상에서 제외.</p>
     */
    public void archive() {
        markArchived();
    }
}