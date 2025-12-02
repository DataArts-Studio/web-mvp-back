package com.data_arts_studio.web_mvp_back.milestone.domain;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.shared.DomainValidators;

public class Milestone extends BaseEntity {
    // 마일스톤 도메인 모델 스켈레톤
    private final MilestoneId id; // 마일스톤 식별자
    private final ProjectId projectId; // 연관된 식별자 (Project)
    private String name; // 마일스톤 이름
    private String description; // 마일스톤 설명
    private MilestoneStatus status; // 마일스톤 상태

    public Milestone(MilestoneId id, ProjectId projectId, String name, String description, MilestoneStatus status) {
        super();
        DomainValidators.requireNonNull(id, "MilestoneId는 null일 수 없습니다.");
        DomainValidators.requireNonNull(projectId, "ProjectId는 null일 수 없습니다.");
        DomainValidators.requireNonEmpty(name, "마일스톤 이름은 null 또는 빈 값일 수 없습니다.");
        
        // if (id == null) {
        //     throw new IllegalArgumentException("MilestoneId는 null일 수 없습니다.");
        // }
        // if (projectId == null) {
        //     throw new IllegalArgumentException("ProjectId는 null일 수 없습니다.");
        // }
        // if (name == null || name.isEmpty()) {
        //     throw new IllegalArgumentException("마일스톤 이름은 null 또는 빈 값일 수 없습니다.");
        // }
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.status = status;
    }
    
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

    public MilestoneStatus getStatus() {
        return status;
    }

    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
        markUpdated();
    }

    public void updateStatus(MilestoneStatus newStatus) {
        this.status = newStatus;
        markUpdated();
    }
}
