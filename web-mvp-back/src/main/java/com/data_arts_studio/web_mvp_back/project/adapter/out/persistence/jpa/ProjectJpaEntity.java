package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// JPA 엔티티 클래스
@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
}
