package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "test_suites")
@Getter
public class TestSuiteJpaEntity {
    @Id
    private String id;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(nullable = false)
    private String name;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected TestSuiteJpaEntity() {}

    public TestSuiteJpaEntity(String id, String projectId, String name, int sortOrder, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

}

