package com.data_arts_studio.web_mvp_back.project.application.validator;

import com.data_arts_studio.web_mvp_back.shared.exception.BaseException;

public class ProjectBusinessException extends BaseException {

    // enum ErrorCode 기반 예외 생성
    public ProjectBusinessException(ProjectErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getErrorCode(), errorCode.getHttpStatus() );
    }
    
}
