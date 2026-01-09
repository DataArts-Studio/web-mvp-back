package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;

// 프로젝트 존재 여부를 확인하는 출력 포트 인터페이스
public interface CheckProjectExistsPort {
    boolean existsById(String projectId);
}
