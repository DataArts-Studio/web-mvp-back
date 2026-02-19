package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_run_suites")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TestRunSuiteJpaEntity {
    @EmbeddedId
    private TestRunSuiteJpaId id;
}
