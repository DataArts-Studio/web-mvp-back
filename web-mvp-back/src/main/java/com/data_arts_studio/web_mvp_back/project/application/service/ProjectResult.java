package com.data_arts_studio.web_mvp_back.project.application.service;

// 서비스에서 반환할 DTO 클래스 
public class ProjectResult {
    private final String projectId;
    private final String name;
    private final String description;
    private final String ownerName;

    public ProjectResult(String projectId, String name, String description, String ownerName) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.ownerName = ownerName;
    }

    public String getProjectId() {
        return projectId;
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
}
