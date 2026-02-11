package com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.request;

import java.util.List;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestPriority;

/**
 * 테스트 케이스 수정 요청 모델
 * @param testSuiteId   테스트 스위트 ID (null 가능)
 * @param name          테스트 케이스 이름
 * @param priority      테스트 케이스 우선순위
 * @param testType      테스트 케이스 유형
 * @param tags          테스트 케이스 태그 목록
 * @param preCondition  테스트 케이스 사전 조건
 * @param steps         테스트 케이스 수행 단계
 * @param expectedResult 테스트 케이스 예상 결과
 */
public record UpdateTestCaseRequest(
    String testSuiteId,
    String name,
    TestPriority priority,
    String testType,        
    List<String> tags,      
    String preCondition,    
    String steps,           
    String expectedResult   
) {
    
}
