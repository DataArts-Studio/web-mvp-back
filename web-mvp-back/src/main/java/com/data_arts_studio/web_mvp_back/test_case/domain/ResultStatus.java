package com.data_arts_studio.web_mvp_back.test_case.domain;

// 테스트 케이스 결과의 상태를 나타내는 enum 클래스
public enum ResultStatus{
    UNTESTED, // 미실행
    PASSED, // 통과 
    FAILED, // 실패
    BLOCKED // 차단된 상태
}
