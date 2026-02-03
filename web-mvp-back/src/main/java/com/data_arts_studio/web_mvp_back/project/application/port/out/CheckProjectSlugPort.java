package com.data_arts_studio.web_mvp_back.project.application.port.out;

// 프로젝트 슬로그 관련 검증 포트
public interface CheckProjectSlugPort {
    // 슬러그가 있는지 없는지 중복 체크 
    boolean existsBySlug(String slug);
}
