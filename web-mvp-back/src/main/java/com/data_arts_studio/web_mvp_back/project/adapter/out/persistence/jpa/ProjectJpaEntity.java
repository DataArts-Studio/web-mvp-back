package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// JPA 엔티티 클래스
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
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    protected ProjectJpaEntity() {}

    public ProjectJpaEntity(String id, String name, String password, String description, String ownerName, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.description = description;
        this.ownerName = ownerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
    
    public String getId() {
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUppdatedAt() {
        return updatedAt;
    }
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}

