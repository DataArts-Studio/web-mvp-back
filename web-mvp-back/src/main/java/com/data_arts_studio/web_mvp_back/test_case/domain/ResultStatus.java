package com.data_arts_studio.web_mvp_back.test_case.domain;

// 테스트 케이스 결과 상태를 나타내는 enum class
public enum ResultStatus {
    UNTESTED, // 미실행
    PASS,    // 성공
    FAIL,     // 실패
    BLOCKED  // 차단
}
