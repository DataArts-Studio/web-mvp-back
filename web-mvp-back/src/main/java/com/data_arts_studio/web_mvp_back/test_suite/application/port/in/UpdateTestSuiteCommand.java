package com.data_arts_studio.web_mvp_back.test_suite.application.port.in;

import lombok.Builder;

/**
 * 테스트 스위트 수정 커맨드
 * @param testSuiteId 테스트 스위트 ID
 * @param name 테스트 스위트 이름
 * @param description 테스트 스위트 설명
 */
@Builder
public record UpdateTestSuiteCommand(
    String testSuiteId,
    String name,
    String description
) {
    
}
