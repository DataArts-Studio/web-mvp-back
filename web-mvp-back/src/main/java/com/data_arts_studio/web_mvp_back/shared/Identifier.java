package com.data_arts_studio.web_mvp_back.shared;

import java.util.UUID;

// UUID 기반 Id VO abstract class
public abstract class Identifier {
    private final Long id;  

    protected Identifier(Long id) {
        this.id = id;
    }

    // UUID 생성 유틸리티 메서드
    // Entitiy의 ID를 값 객체로 생성
    public static String newId(String id) {
        // 랜덤 UUID 생성
        return UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }
    
}
