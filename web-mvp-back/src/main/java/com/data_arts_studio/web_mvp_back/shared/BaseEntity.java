package com.data_arts_studio.web_mvp_back.shared;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    // 모든 엔티티가 공통으로 가져야할 필드
    protected LocalDateTime createdAt; // 생성일시
    protected LocalDateTime updatedAt; // 수정일시
    protected LocalDateTime archivedAt; // 아카이브 일시 
    
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.archivedAt = null; // 삭제되지 않으면 null 처리 
    }

    public void restoreAuditFields(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime archivedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.archivedAt = archivedAt;
    }

    public void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markArchived() {
        this.archivedAt = LocalDateTime.now();
    }

    // 삭제 복원 메서드
    public void restore() {
        this.archivedAt = null;
        this.markUpdated();
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getArchivedAt() {
        return archivedAt;
    }

}