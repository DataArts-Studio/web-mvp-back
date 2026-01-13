package com.data_arts_studio.web_mvp_back.test_run.domain;

// 테스트 런의 상태를 나타내는 enum 클래스
public enum TestRunStatus {
    PENDING, // 대기 중
    RUNNING, // 실행 중
    COMPLETED, // 완료됨
    ABORTED // 중단됨
}
