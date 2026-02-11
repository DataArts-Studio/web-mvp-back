package com.data_arts_studio.web_mvp_back.test_case.adapter.in.web.response;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;

/**
 * 테스트 케이스 수정 응답 모델
 * @param id               테스트 케이스 ID
 * @param caseKey         테스트 케이스 키
 * @param projectId        프로젝트 ID
 * @param testSuiteId     테스트 스위트 ID
 * @param testSuiteName   테스트 스위트 이름
 * @param name            테스트 케이스 이름
 * @param testType       테스트 케이스 유형
 * @param resultStatus   테스트 케이스 결과 상태
 * @param tags            테스트 케이스 태그 목록
 * @param preCondition    테스트 케이스 사전 조건
 * @param steps           테스트 케이스 수행 단계
 * @param expectedResult  테스트 케이스 예상 결과
 * @param updatedAt      수정 일시
 */
@Builder
public record UpdateTestCaseResponse(
    String id,
    String caseKey,
    String projectId,

    String testSuiteId,
    String testSuiteName,

    String name,
    String testType,
    String resultStatus,

    List<String> tags,
    String preCondition,
    String steps,
    String expectedResult,
    OffsetDateTime updatedAt
) {
    
}
