package com.data_arts_studio.web_mvp_back.test_suite.application.service;

import lombok.Builder;

/**
 * 테스트 스위트 수정 결과 서비스 모델
 * @param id            테스트 스위트 ID
 * @param projectId     프로젝트 ID
 * @param name          테스트 스위트 이름
 * @param description   테스트 스위트 설명
 */
@Builder
public record UpdateTestSuiteResult(
    String id,
    String projectId,
    String name,
    String description
) {
    
}
