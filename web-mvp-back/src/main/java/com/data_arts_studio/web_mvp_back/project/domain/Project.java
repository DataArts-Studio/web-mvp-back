package com.data_arts_studio.web_mvp_back.project.domain;

import java.time.OffsetDateTime;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;


public class Project extends BaseEntity {
    // 프로젝트 도메인 모델 스켈레톤
    private final ProjectId id; // 프로젝트 식별자
    private String name; // 프로젝트 이름
    private String identifier; //프로젝트의 해시된 비밀번호 
    private String description; // 프로젝트 설명
    private String ownerName; // 프로젝트 소유자 이름

    // 프로젝트 생성용 생성자
    public Project(ProjectId id, String name, String identifier ,String description, String ownerName) { 
        super();
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.description = description;
        this.ownerName = ownerName;
    }
    // JPA 리스톨용 생성자
    public Project(ProjectId id, String name, String identifier ,String description, String ownerName, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime archivedAt) { 
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.description = description;
        this.ownerName = ownerName;
        this.restoreAuditFields(createdAt, updatedAt, archivedAt);
    }

    // Getter 메서드
    public ProjectId getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getIdentifier() { 
        return identifier;
    }
    public String getDescription() {
        return description;
    }
    public String getOwnerName() {
        return ownerName;
    }
    // 프로젝트 이름 수정
    public void rename (String newName) {
        this.name = newName;
        markUpdated();
    }
    // 프로젝트 비밀번호 변경
    public void changeIdentifier (String newIdentifier) {
        this.identifier = newIdentifier;
        markUpdated();
    }
    //  프로젝트 설명 변경
    public void updateDescription (String newDescription) {
        this.description = newDescription;
        markUpdated();
    }

    // 프로젝트 아카이브
    public void archive() {
        this.markArchived();
    }
}
