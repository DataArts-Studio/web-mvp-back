package com.data_arts_studio.web_mvp_back.project.application.service;

import java.time.LocalDateTime;

// 서비스에서 반환할 DTO 클래스
public record  ProjectResult (
        String projectId,
        String name,
        String description,
        String ownerName,
        LocalDateTime createdAt){
}
