package com.data_arts_studio.web_mvp_back.project.domain;

import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.shared.DomainValidators;

public class Project extends BaseEntity {
    // 프로젝트 도메인 모델 스켈레톤
    private final ProjectId id; // 프로젝트 식별자
    private String name; // 프로젝트 이름
    private String description; // 프로젝트 설명
    private String ownerName; // 프로젝트 소유자 이름

    public Project(ProjectId id, String name, String description, String ownerName) {
        super();
        DomainValidators.requireNonNull(id, "ProjectId는 null일 수 없습니다.");
        DomainValidators.requireNonEmpty(name, "프로젝트 이름은 null 또는 빈 값일 수 없습니다.");
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerName = ownerName;
    }

    public ProjectId getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void rename (String newName) {
        this.name = newName;
        markUpdated();
    }

    public void updateDescription (String newDescription) {
        this.description = newDescription;
        markUpdated();
    }

}
