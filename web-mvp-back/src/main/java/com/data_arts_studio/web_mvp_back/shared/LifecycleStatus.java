package com.data_arts_studio.web_mvp_back.shared;

// 모든 주요 엔티티에 적용되는 라이플 사이클 상태 enum 클래스
public enum LifecycleStatus {
    ACTIVE, // 활성 상태 
    ARCHIVED, // 보관 상태 (논리적 삭제)
    DELETED // 삭제 상태 (물리적 삭제)
}
