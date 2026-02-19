package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suite_test_cases")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SuiteTestCaseJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "suite_id", nullable = false)
    private String suiteId;

    @Column(name = "test_case_id", nullable = false)
    private String testCaseId;
}
