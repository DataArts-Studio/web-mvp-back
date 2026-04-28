package com.data_arts_studio.web_mvp_back.test_run.application.port.out;

/**
 * 테스트 런 이름 중복 여부를 조회하는 포트
 */
public interface CheckTestRunNamePort {

    /**
     * 같은 프로젝트의 활성 테스트 런 이름 중복 여부를 조회
     *
     * @param projectId 프로젝트 식별자
     * @param name 테스트 런 이름
     * @return 중복 여부
     */
    boolean isTestRunNameDuplicated(String projectId, String name);
}
