package com.data_arts_studio.web_mvp_back.test_case.application.service;

import java.util.List;
import com.data_arts_studio.web_mvp_back.test_case.domain.ResultStatus;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestPriority;


/**
 * 테스트 케이스 결과 서비스 모델
 * @param id               테스트 케이스 ID
 * @param projectId        프로젝트 ID
 * @param testSuiteId     테스트 스위트 ID
 * @param caseKey         테스트 케이스 키
 * @param name            테스트 케이스 이름
 * @param priority        테스트 케이스 우선순위
 * @param testType       테스트 케이스 유형
 * @param status          테스트 케이스 상태
 * @param tags            테스트 케이스 태그 목록
 * @param preCondition    테스트 케이스 사전 조건
 * @param steps           테스트 케이스 수행 단계
 * @param expectedResult  테스트 케이스 예상 결과
 */
public record TestCaseResult(
    String id,         
    String projectId,     
    String testSuiteId,  
    String caseKey,      
    String name,         
    TestPriority priority,
    String testType,      
    ResultStatus status, 
    List<String> tags,        
    String preCondition,  
    String steps,         
    String expectedResult ) {}