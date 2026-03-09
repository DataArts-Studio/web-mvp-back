package com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.data_arts_studio.web_mvp_back.shared.LifecycleStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// JPA 엔티티 클래스
@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor (access = lombok.AccessLevel.PRIVATE)
@Builder
public class ProjectJpaEntity {

    @Id
    private UUID id; 

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

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_status", nullable = false)
    private LifecycleStatus lifecycleStatus;
}
