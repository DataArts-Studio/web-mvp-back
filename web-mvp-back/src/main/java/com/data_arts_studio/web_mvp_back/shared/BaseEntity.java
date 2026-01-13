package com.data_arts_studio.web_mvp_back.shared;

import java.time.OffsetDateTime;

public abstract class BaseEntity {
    // 모든 엔티티가 공통으로 가져야할 필드
    protected OffsetDateTime createdAt; // 생성일시
    protected OffsetDateTime updatedAt; // 수정일시
    protected OffsetDateTime archivedAt; // 아카이브 일시 
    protected LifecycleStatus lifecycleStatus; // 라이프사이클 상태
    
    protected BaseEntity() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
        this.lifecycleStatus = LifecycleStatus.ACTIVE; // 기본 상태 ACTIVE
    }

    // 감사 필드 복원 메서드
    public void restoreAuditFields(OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime archivedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.archivedAt = archivedAt;
    }
    // 수정 일시 갱신 메서드
    protected void markUpdated() {
        this.updatedAt = OffsetDateTime.now();
    }
    // 아카이브 메서드
    public void markArchived() {
        this.lifecycleStatus = LifecycleStatus.ARCHIVED;
        this.archivedAt = OffsetDateTime.now();
        this.markUpdated();
    }
    // 삭제 복원 메서드
    public void restore() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
        this.archivedAt = null;
        this.markUpdated();
    }

    //  Getter 메서드
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
    public OffsetDateTime getArchivedAt() {
        return archivedAt;
    }
    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

}