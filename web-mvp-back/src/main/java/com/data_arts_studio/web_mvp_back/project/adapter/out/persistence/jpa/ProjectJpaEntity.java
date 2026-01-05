package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import java.time.LocalDateTime;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

// JPA 엔티티 클래스
@Getter
@Entity
@Table(name = "projects")
public class ProjectJpaEntity {
    @Id
    private String id; 

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;
    
    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(nullable = false)
    private ProjectStatus status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    protected ProjectJpaEntity() {}

    public ProjectJpaEntity(String id, String name, String password, String description, String ownerName, ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.description = description;
        this.ownerName = ownerName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}

