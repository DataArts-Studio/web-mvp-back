package com.data_arts_studio.web_mvp_back.shared;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    // 모든 엔티티가 공통으로 가져야할 생성과 수정 타임스탬프 필드
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    // 삭제 타임스탬프 필드 
    protected LocalDateTime deletedAt;
    
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deletedAt = null; // 삭제되지 않으면 null 처리 
    }

    public void restoreAuditFields(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

    // 삭제 복원 메서드
    public void restore() {
        this.deletedAt = null;
        this.markUpdated();
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

}