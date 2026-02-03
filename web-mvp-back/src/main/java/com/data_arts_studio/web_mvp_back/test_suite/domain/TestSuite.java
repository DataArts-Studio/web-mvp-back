package com.data_arts_studio.web_mvp_back.test_suite.domain;

import java.time.OffsetDateTime;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.shared.BaseEntity;

// 테스트 스위트 도메인 모델 스켈레톤
public class TestSuite extends BaseEntity {
    private final TestSuiteId id; // 테스트 스위트 식별자
    private final ProjectId projectId; // 연관 식별자 (ProjectId)

    private String name; // 테스트 스위트 이름
    private String description; // 테스트 스위트 설명
    private int sortOrder; // 프로젝트 내 노출/정렬 순서

    // 테스트 스위트 신규 생성 시에 사용하는 생성자 
    public TestSuite(TestSuiteId id, ProjectId projectId, String name, String description, int sortOrder) { 
        super();
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
    }
    // 기존 테스트 스위트 복원 시에 사용하는 생성자
    public TestSuite(TestSuiteId id, ProjectId projectId, String name, int sortOrder, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime archivedAt) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.sortOrder = sortOrder;
        restoreAuditFields(createdAt, updatedAt, archivedAt);
    }
    // Getter 메서드
    public TestSuiteId getId() {
        return id;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSortOrder() {
        return sortOrder;
    }
    // 테스트 스위트 이름 변경
    public void rename(String newName) {
        this.name = newName;
        markUpdated();
    }
    // 테스트 스위트 설명 변경
    public void updateDescription(String newDescription) {
        this.description = newDescription;
        markUpdated();
    }
    // 테스트 스위트 정렬 순서 변경
    public void updateSortOrder(int newSortOrder) {
        this.sortOrder = newSortOrder;
        markUpdated();
    }
    // 테스트 스위트 아카이브 
    public void archive(){
        this.markArchived();
    }
}
