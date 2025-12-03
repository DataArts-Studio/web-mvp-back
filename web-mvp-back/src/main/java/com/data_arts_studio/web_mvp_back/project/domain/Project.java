package com.data_arts_studio.web_mvp_back.project.domain;

import com.data_arts_studio.web_mvp_back.shared.BaseEntity;

public class Project extends BaseEntity {
    // 프로젝트 도메인 모델 스켈레톤
    private final ProjectId id; // 프로젝트 식별자
    // private boolean isPravateMode; // 프라이빗 여부
    // private String password; // 프라이빗 모드일 경우 비밀번호
    private String name; // 프로젝트 이름
    private String description; // 프로젝트 설명
    private String ownerName; // 프로젝트 소유자 이름

    public Project(ProjectId id, String name, String description, String ownerName) {  //boolean isPravateMode, String password,
        super();
        // this.isPravateMode = isPravateMode;
        // this.password = password;
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
