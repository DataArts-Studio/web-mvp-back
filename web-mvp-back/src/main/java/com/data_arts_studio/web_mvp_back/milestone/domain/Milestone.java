package com.data_arts_studio.web_mvp_back.milestone.domain;

import java.time.OffsetDateTime;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;

// 마일스톤 도메인 모델 스켈레톤
public class Milestone extends BaseEntity {
    private final MilestoneId id; // 마일스톤 식별자
    private final ProjectId projectId; // 연관된 식별자 (Project)

    private String name; // 마일스톤 이름
    private String description; // 마일스톤 설명

    private OffsetDateTime startDate; // 마일스톤 시작일
    private OffsetDateTime endDate; // 마일스톤 종료일
    private MilestoneProgressStatus milestoneProgressStatus; // 마일스톤 상태

    public Milestone(MilestoneId id, ProjectId projectId, String name, String description, OffsetDateTime startDate, OffsetDateTime endDate, MilestoneProgressStatus milestoneProgressStatus) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.milestoneProgressStatus = milestoneProgressStatus;
    }
    // Getter 메서드
    public MilestoneId getId() {
        return id;
    }
    public ProjectId getProjectId() {
        return projectId;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public OffsetDateTime getStartDate() {
        return startDate;
    }
    public OffsetDateTime getEndDate() {
        return endDate;
    }
    public MilestoneProgressStatus getStatus() {
        return milestoneProgressStatus;
    }
    // 마일스톤 이름 변경
    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }
    // 마일스톤 설명 변경
    public void updateDescription(String newDescription) {
        this.description = newDescription;
        markUpdated();
    }
    // 마일스톤 상태 변경
    public void updateStatus(MilestoneProgressStatus newStatus) {
        this.milestoneProgressStatus = newStatus;
        markUpdated();
    }
}
