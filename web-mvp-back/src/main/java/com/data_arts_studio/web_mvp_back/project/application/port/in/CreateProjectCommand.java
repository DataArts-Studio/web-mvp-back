package com.data_arts_studio.web_mvp_back.project.application.port.in;

import lombok.Builder;

/**
 * 프로젝트 생성 커맨드
 * @param name 프로젝트 이름 (1-50자)
 * @param identifier 식별번호 (해싱 전 원본, 4자 이상)
 * @param identifierConfirm 일치 확인용 식별번호 재입력 
 * @param description 프로젝트 설명 (필수 아님)
 * @param ownerName 소유자 이름 (필수 아님))
 */
@Builder
public record CreateProjectCommand(
    String name,
    String identifier, 
    String identifierConfirm,// 비밀번호 확인
    String description,
    String ownerName
) {}
