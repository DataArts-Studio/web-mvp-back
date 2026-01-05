package com.data_arts_studio.web_mvp_back.project.adapter.in.web.request;

// 아카이브 확인 시 사용자가 입력한 프로젝트 이름을 받은 요청 객체
public record ArchiveProjectRequest(
    String confirmName
) {
    
}
