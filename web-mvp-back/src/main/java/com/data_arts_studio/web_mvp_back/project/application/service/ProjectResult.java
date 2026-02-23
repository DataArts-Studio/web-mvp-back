package com.data_arts_studio.web_mvp_back.project.application.service;

import java.time.OffsetDateTime;

/**
 * 프로젝트 생성 결과를 나타내는 DTO 클래스
 * @param projectId   생성된 프로젝트의 ID
 * @param name        생성된 프로젝트의 이름
 * @param description 생성된 프로젝트의 설명
 * @param ownerName   생성된 프로젝트의 소유자 이름
 * @param createdAt   생성된 프로젝트의 생성 시간
 */
public record ProjectResult (
        String projectId,
        String name,
        String description,
        String ownerName,
        OffsetDateTime createdAt){}