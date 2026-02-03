package com.data_arts_studio.web_mvp_back.project.application.validator;

import org.springframework.http.HttpStatus;
import com.data_arts_studio.web_mvp_back.shared.exception.ErrorCode;

/**
 * 프로젝트의 모든 비즈니스 예외 코드와 HTTP 상태 코드를 매핑하는 Enum 클래스 
 */
public enum ProjectErrorCode implements ErrorCode {
    // 프로젝트 생성 관련 예외 
    PROJECT_NAME_DUPLICATED("P409001", "이미 존재하는 프로젝트 이름입니다.", HttpStatus.CONFLICT), 
    PROJECT_NAME_EMPTY_VALUE("P40098", "프로젝트 이름을 입력해주세요.", HttpStatus.BAD_REQUEST),
    PROJECT_NAME_LENGTH_EXCEEDED("P40097", "프로젝트 이름은 50자 이하로 입력해주세요.", HttpStatus.BAD_REQUEST),

    PROJECT_PASSWORD_EMPTY_VALUE("P40080", "프로젝트 식별번호를 입력해주세요.", HttpStatus.BAD_REQUEST),
    PROJECT_PASSWORD_MISMATCH("P40081", "프로젝트 식별번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST), 
    PROJECT_PASSWORD_TOO_SHORT("P40082", "4자 이상 입력해주세요.", HttpStatus.BAD_REQUEST),
    
    // 프로젝트 아카이브 관련 예외 
    PROJECT_NOT_FOUND("P404001", "해당 프로젝트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PROJECT_ALREADY_ARCHIVED("P409002", "이미 아카이브된 프로젝트입니다.", HttpStatus.CONFLICT),
    PROJECT_ARCHIVE_NAME_MISMATCH("P40099", "프로젝트 이름이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    ProjectErrorCode(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
    
    @Override
    public String getErrorCode() { 
        return errorCode; 
    }

    @Override
    public String getMessage() { 
        return message; 
    }

    @Override
    public HttpStatus getHttpStatus() { 
        return httpStatus; 
    }
}