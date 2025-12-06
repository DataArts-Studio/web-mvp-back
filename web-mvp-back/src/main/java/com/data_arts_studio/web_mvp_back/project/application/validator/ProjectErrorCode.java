package com.data_arts_studio.web_mvp_back.project.application.validator;

import org.springframework.http.HttpStatus;

/**
 * 프로젝트의 모든 비즈니스 예외 코드와 HTTP 상태 코드를 매핑하는 Enum 클래스 
 */
public enum ProjectErrorCode {
    // 문서번호: FDD-MVP-001: 프로젝트 생성 관련 예외 (Prefix: P4XX)
    PROJECT_NAME_DUPLICATED("P409001", "이미 존재하는 프로젝트 이름입니다.", HttpStatus.CONFLICT), 
    PROJECT_NAME_EMPTY_VALUE("P40098", "프로젝트 이름을 입력해주세요.", HttpStatus.BAD_REQUEST),
    PROJECT_NAME_LENGTH_EXCEEDED("P40097", "프로젝트 이름은 50자 이하로 입력해주세요.", HttpStatus.BAD_REQUEST),

    PROJECT_PASSWORD_EMPTY_VALUE("P40080", "프로젝트 식별번호를 입력해주세요.", HttpStatus.BAD_REQUEST),
    PROJECT_PASSWORD_MISMATCH("P40081", "프로젝트 식별번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST), 
    PROJECT_PASSWORD_TOO_SHORT("P40082", "4자 이상 입력해주세요.", HttpStatus.BAD_REQUEST),
    
    // 공통/시스템 에러 (Prefix: C5XX)
    INTERNAL_SERVER_ERROR("C50000", "서버 오류가 발생했습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    ProjectErrorCode(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
    
    public String getErrorCode() { 
        return errorCode; 
    }

    public String getMessage() { 
        return message; 
    }

    public HttpStatus getHttpStatus() { 
        return httpStatus; 
    }
}