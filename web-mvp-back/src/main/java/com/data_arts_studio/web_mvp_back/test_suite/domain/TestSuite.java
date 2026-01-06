package com.data_arts_studio.web_mvp_back.test_suite.domain;

import java.time.LocalDateTime;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;

public class TestSuite extends BaseEntity {
    // 테스트 스위트 도메인 모델 스켈레톤
    private final TestSuiteId id; // 테스트 스위트 식별자
    private final ProjectId projectId; // 연관 식별자 (ProjectId)
    private String name; // 테스트 스위트 이름
    // private String description; // 테스트 스위트 설명
    private int sortOrder; // 프로젝트 내 노출/정렬 순서

    // 테스트 스위트 신규 생성 시에 사용하는 생성자 
    public TestSuite(TestSuiteId id, ProjectId projectId, String name, Integer sortOrder) { // , String description 추 후 필요 시 추가 가능
        super();
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        // this.description = description;
        this.sortOrder = sortOrder;
    }
    // 기존 테스트 스위트 복원 시에 사용하는 생성자
    public TestSuite(TestSuiteId id, ProjectId projectId, String name, Integer sortOrder, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.sortOrder = sortOrder;
        restoreAuditFields(createdAt, updatedAt, deletedAt);
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
