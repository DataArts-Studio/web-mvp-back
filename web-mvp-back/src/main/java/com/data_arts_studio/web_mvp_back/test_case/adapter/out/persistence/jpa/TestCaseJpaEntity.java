package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.data_arts_studio.web_mvp_back.shared.LifecycleStatus;
import com.data_arts_studio.web_mvp_back.test_case.domain.ResultStatus;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestPriority;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_cases")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "test_suite_id", nullable = true)
    private String testSuiteId;

    @Column(name = "case_key", nullable = false)
    private String caseKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TestPriority priority;

    @Column(name = "test_type")
    private String testType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "test_case_tags", joinColumns = @JoinColumn(name = "test_case_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Column(name = "pre_condition", columnDefinition = "TEXT")
    private String preCondition;

    @Column(name = "steps", columnDefinition = "TEXT")
    private String steps;

    @Column(name = "expected_result", columnDefinition = "TEXT")
    private String expectedResult;

    @Column(name = "sort_order")
    private int sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_status")
    private ResultStatus resultStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_status")
    private LifecycleStatus lifecycleStatus;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "archived_at")
    private OffsetDateTime archivedAt;
}
