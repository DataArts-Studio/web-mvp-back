package com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response;

import java.time.OffsetDateTime;
import java.util.List;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestPriority;

import lombok.Builder;

/**
 * 테스트 케이스 상세 응답 모델
 * @param id               테스트 케이스 식별자
 * @param projectId        소속 프로젝트 식별자
 * @param caseKey          테스트 케이스 키
 * @param resultStatus     테스트 케이스 결과 상태
 * @param name             테스트 케이스 이름
 * @param testSuiteId      소속 테스트 스위트 식별자
 * @param testSuiteName    소속 테스트 스위트 이름
 * @param priority         테스트 케이스 우선순위
 * @param testType         테스트 케이스 유형
 * @param createdAt        생성 일시
 * @param updatedAt        수정 일시
 * @param tags             태그 목록
 * @param preCondition     테스트 케이스 사전 조건
 * @param steps            테스트 케이스 단계
 * @param expectedResult   테스트 케이스 기대 결과
 */
@Builder
public record TestCaseDetailRespose(
    String id,
    String projectId,

    String caseKey,
    String resultStatus,
    String name,
    // 스위트 정보
    String testSuiteId,
    String testSuiteName,
    TestPriority priority,
    String testType,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    List<String> tags,

    String preCondition,
    String steps,
    String expectedResult
    
) {} 
