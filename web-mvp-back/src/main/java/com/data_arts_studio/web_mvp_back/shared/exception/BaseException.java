package com.data_arts_studio.web_mvp_back.shared.exception;

public abstract class BaseException extends RuntimeException {
    private final String errorCode; // 에러 코드
    private final int status; // HTTP 상태 코드

    protected BaseException(String errorCode, int status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getStatus() {
        return status;
    }

    
}
