package com.data_arts_studio.web_mvp_back.project.domain;

import java.time.LocalDateTime;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;


public class Project extends BaseEntity {
    // 프로젝트 도메인 모델 스켈레톤
    private final ProjectId id; // 프로젝트 식별자
    private String name; // 프로젝트 이름
    private String password; //프로젝트 비밀번호 
    private String description; // 프로젝트 설명
    private String ownerName; // 프로젝트 소유자 이름
    private ProjectStatus status = ProjectStatus.ACTIVE; // 프로젝트 상태


    // 프로젝트 생성용 생성자
    public Project(ProjectId id, String name, String password, String description, String ownerName, ProjectStatus status) { 
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.description = description;
        this.ownerName = ownerName;
        this.status = status;
    }
    // JPA 리스톨용 생성자
    public Project(ProjectId id, String name,  String password, String description, String ownerName, ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) { 
        this.id = id;
        this.name = name;
        this.password = password;
        this.description = description;
        this.ownerName = ownerName;
        this.status = status;
        this.restoreAuditFields(createdAt, updatedAt, deletedAt);
        
    }

    public ProjectId getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getDescription() {
        return description;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public ProjectStatus getStatus() {
        return status;
    }

    // 프로젝트 수정
    public void rename (String newName) {
        this.name = newName;
        markUpdated();
    }
    // 비밀번호 변경
    public void changePassword (String newPassword) {
        this.password = newPassword;
        markUpdated();
    }
    // 설명 변경
    public void updateDescription (String newDescription) {
        this.description = newDescription;
        markUpdated();
    }
    // 프로젝트 아카이브
    public void archive(String confirmName) {
        this.status = ProjectStatus.ARCHIVED;
        this.markUpdated();
    }

}
