package com.data_arts_studio.web_mvp_back.shared;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    // 모든 엔티티가 공통으로 가져야할 생성과 수정 타임스탬프 필드
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}