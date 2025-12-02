package com.data_arts_studio.web_mvp_back.test_suite.domain;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;
import com.data_arts_studio.web_mvp_back.shared.DomainValidators;

public class TestSuite extends BaseEntity {
    // 테스트 스위트 도메인 모델 스켈레톤
    private final TestSuiteId id; // 테스트 스위트 식별자
    private final ProjectId projectId; // 연관 식별자 (ProjectId)
    private String name; // 테스트 스위트 이름
    private int sortOrder; // 프로젝트 내 노출/정렬 순서

    public TestSuite(TestSuiteId id, ProjectId projectId, String name, Integer sortOrder) {
        super();
        DomainValidators.requireNonNull(id, "TestSuiteId는 null일 수 없습니다.");
        DomainValidators.requireNonNull(projectId, "ProjectId는 null일 수 없습니다.");
        DomainValidators.requireNonEmpty(name, "테스트 스위트 이름은 null 또는 빈 값일 수 없습니다.");
        
        // if (id == null) {
        //     throw new IllegalArgumentException("TestSuiteId는 null일 수 없습니다.");
        // }
        // if (projectId == null) {
        //     throw new IllegalArgumentException("ProjectId는 null일 수 없습니다.");
        // }
        // if (name == null || name.isEmpty()) {
        //     throw new IllegalArgumentException("테스트 스위트 이름은 null 또는 빈 값일 수 없습니다.");
        // }

        // // sortOrder는 NOT NULL, DEFAULT 0, 음수 불가 하여야 한다.
        // if (sortOrder == null) {
        //     this.sortOrder = 0;
        // } else if (sortOrder < 0) {
        //     throw new IllegalArgumentException("sortOrder는 0 이상이어야 합니다.");
        // } else {
        //     this.sortOrder = sortOrder;
        // }

        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.sortOrder = DomainValidators.normalizeSortOrder(sortOrder);
    }
    
    public TestSuiteId getId() {
        return id;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }

    public void updateSortOrder(int newSortOrder) {
        this.sortOrder = newSortOrder;
        markUpdated();
    }
}
