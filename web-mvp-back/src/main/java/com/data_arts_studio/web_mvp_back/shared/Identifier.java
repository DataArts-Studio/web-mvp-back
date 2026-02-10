package com.data_arts_studio.web_mvp_back.shared;

import java.util.UUID;

// UUID 기반 Id VO abstract class
public abstract class Identifier {

    private final String id;  

    protected Identifier(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Identifier는 null 또는 빈 값일 수 없습니다.");   
        }
        this.id = id;
    }

    // UUID 생성 유틸리티 메서드
    // Entitiy의 ID를 값 객체로 생성
    public static String newId() {
        // 랜덤 UUID 생성
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 동일 참조 체크
        if (o == null || getClass() != o.getClass()) return false; // 타입 체크
        Identifier that = (Identifier) o; // 타입 체크를 위한 다운 케스팅 
        return id.equals(that.id); // 내부 id 값 비교
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
