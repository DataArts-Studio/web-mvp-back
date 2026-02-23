package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

// JPA 엔티티 클래스
@Entity
@Table(name = "projects")
@Getter
public class ProjectJpaEntity {

    @Id
    private String id; 

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private String identifier;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "archived_at")
    private OffsetDateTime archivedAt;

    protected ProjectJpaEntity() {}

    public ProjectJpaEntity(
        String id,
        String name,
        String identifier,
        String description,
        String ownerName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime archivedAt
    ) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.description = description;
        this.ownerName = ownerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.archivedAt = archivedAt;
    }
}
