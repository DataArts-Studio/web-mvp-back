package com.data_arts_studio.web_mvp_back.test_suite.application.service;

import java.time.OffsetDateTime;


// 테스트 스위트 생성 결과를 나타내는 레코드
public record TestSuiteResult(
    String id, // TestSuiteId
    String projectId, 
    String name,  
    String description,
    OffsetDateTime createdAt
) {
    
}
