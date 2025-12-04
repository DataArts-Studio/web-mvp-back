package com.data_arts_studio.web_mvp_back.project.application.port.in;

// 프로젝트 생성 요청 시에 필요한 커맨드 객체 (입력)
public record CreateProjectCommand(
    String name,
    // boolean isPravateMode, // 프라이빗 여부
    String password, 
    // String passwordConfirm,// 프라이빗 모드일 경우 비밀번호 확인
    String description,
    String ownerName
) {
    
}
