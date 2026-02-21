package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa;

import java.time.OffsetDateTime;

import com.data_arts_studio.web_mvp_back.shared.LifecycleStatus;
import com.data_arts_studio.web_mvp_back.test_case.domain.ResultStatus;
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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(name = "test_type")
    private String testType;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags") // , columnDefinition = "text[]" // PostgreSQL의 배열 타입을 명시적으로 지정 (아마 이놈 때문인듯?)
    private String[] tags;

    @Column(name = "pre_condition", columnDefinition = "TEXT")
    private String preCondition;

    @Column(name = "steps", columnDefinition = "TEXT")
    private String steps;

    @Column(name = "expected_result", columnDefinition = "TEXT")
    private String expectedResult;

    @Column(name = "display_id")
    private Integer displayId;

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
