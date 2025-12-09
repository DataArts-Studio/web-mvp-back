package com.data_arts_studio.web_mvp_back.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    private final String errorCode; // 에러 코드
    private final HttpStatus httpStatus; // HTTP 상태 코드
    
    /**
     * @param message 사용자 친화적인 메세지
     * @param errorCode 내부에서 정의한 에러 코드
     * @param httpStatus 클라이언트에 반환될 HTTP 상태 코드
     */
    public BaseException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    
}
