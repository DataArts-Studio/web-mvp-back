package com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.request;

import java.util.List;


/**
 * 테스트 케이스 생성 요청 모델
 * @param testSuiteId     소속 스위트 (없을 수도 있음)
 * @param name            제목
 * @param testType       테스트 유형
 * @param tags            태그 목록
 * @param preCondition    사전 조건
 * @param steps           테스트 단계
 * @param expectedResult  기대 결과
 */
public record CreateTestCaseRequest(
    String testSuiteId,      
    String name,           
    String testType,
    List<String> tags,      
    String preCondition,    
    String steps,          
    String expectedResult   
) {
    
}
