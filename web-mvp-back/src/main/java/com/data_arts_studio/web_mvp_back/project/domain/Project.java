package com.data_arts_studio.web_mvp_back.project.domain;

import com.data_arts_studio.web_mvp_back.shared.BaseEntity;

public class Project extends BaseEntity {
    // 프로젝트 도메인 모델 스켈레톤
    private final ProjectId id; // 프로젝트 식별자
    // private boolean isPravateMode; // 프라이빗 여부
    private String name; // 프로젝트 이름
    private String password; //프로젝트 비밀번호 
    private String description; // 프로젝트 설명
    private String ownerName; // 프로젝트 소유자 이름

    public Project(ProjectId id, String name, String password, String description, String ownerName) {  //boolean isPravateMode, 
        super();
        // this.isPravateMode = isPravateMode;
        this.id = id;
        this.name = name;
        this.password = password;
        this.description = description;
        this.ownerName = ownerName;
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

    public void rename (String newName) {
        this.name = newName;
        markUpdated();
    }

    public void changePassword (String newPassword) {
        this.password = newPassword;
        markUpdated();
    }

    public void updateDescription (String newDescription) {
        this.description = newDescription;
        markUpdated();
    }

}
