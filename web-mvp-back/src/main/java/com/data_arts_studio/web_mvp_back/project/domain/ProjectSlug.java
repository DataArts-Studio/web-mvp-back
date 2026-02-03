package com.data_arts_studio.web_mvp_back.project.domain;

import java.util.UUID;

// 프로젝트 슬러그 값 객체
// URL에서 사용되는 고유 식별자 
public class ProjectSlug {
    private final String value;

    public ProjectSlug(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    // 문자열 값으로부터 슬러그 객체 생성
    public static ProjectSlug from(String value) {
        return new ProjectSlug(value);
    }

    /** 프로젝트 이름 기반으로 슬러그 생성
     * 이 메서드는 확률적 유니크만 제공 
     * 최종 유니크 보장은 서비스 계층에서 
     * @param projectName
     * @return
     */
    public static ProjectSlug fromProjectNameGenerateProjectSlug(String projectName) {
        // 소문자 변환, 공백을 하이픈으로 대체, 특수문자 제거
        String normalized = projectName.toLowerCase().trim().replaceAll("\\s+", "-").replaceAll("[^a-z0-9-]", "");
        // 고유성 보장을 위한 UUID 추가 
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        return new ProjectSlug(normalized + "-" + uniqueSuffix);
    }
}
