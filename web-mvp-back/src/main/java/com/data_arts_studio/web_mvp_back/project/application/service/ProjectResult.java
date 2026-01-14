package com.data_arts_studio.web_mvp_back.project.application.service;

import java.time.OffsetDateTime;

// 서비스에서 반환할 프로젝트 생성 결과 클래스
// URL 생성 메서드 포함
public record ProjectResult (
        String projectId,
        String name,
        String slug,
        String description,
        String ownerName,
        OffsetDateTime createdAt){

        public String getProjectUrl() {
                return "/projects/" + slug;
        }
}
