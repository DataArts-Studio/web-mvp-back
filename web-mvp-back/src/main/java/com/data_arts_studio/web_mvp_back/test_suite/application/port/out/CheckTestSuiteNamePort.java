package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;


// 테스트스위트 이름의 중복 여부를 확인하는 출력 포트
public interface CheckTestSuiteNamePort {
     boolean isTestSuiteNameDuplicated(String projectId, String name);
}
