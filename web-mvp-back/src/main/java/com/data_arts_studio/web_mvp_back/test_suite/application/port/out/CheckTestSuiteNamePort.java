package com.data_arts_studio.web_mvp_back.test_suite.application.port.out;


// 테스트 스위트 이름의 중복 여부를 확인하는 출력 포트
public interface CheckTestSuiteNamePort {
     /**
      * 생성 시에 같은 프로젝트 안에 같은 스위트 이름이 있는지 여부 확인
      * @param projectId
      * @param name
      * @return
      */
     boolean isTestSuiteNameDuplicated(String projectId, String name);
     /**
      * 수정 시에 이름 중복 여부를 확인
      * 같은 프로젝트 안에서 같은 이름의 스위트가 있는지 확인
      * 현재 수정 중인 스위트 id는 제외
      * @param projectId
      * @param name
      * @param excludeSuiteId
      * @return
      */
     boolean isTestSuiteNameDuplicatedExceptId(String projectId, String name, String excludeSuiteId);
}
