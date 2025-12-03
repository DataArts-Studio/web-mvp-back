package com.data_arts_studio.web_mvp_back.project.application.port.out;

// 프로젝트 이름 관련 검증 포트 
public interface CheckProjectNamePort {
    // 이름 중복 여부 확인 (true: 중복, false: 중복 x)
    boolean isProjectNameDuplicated(String name);
}
