package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TestRunSuiteJpaId implements Serializable {
    @Column(name = "test_run_id", nullable = false)
    private String testRunId;

    @Column(name = "test_suite_id", nullable = false)
    private String testSuiteId;
}
