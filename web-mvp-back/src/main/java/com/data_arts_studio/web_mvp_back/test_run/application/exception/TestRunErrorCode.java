package com.data_arts_studio.web_mvp_back.test_run.application.exception;

import org.springframework.http.HttpStatus;

import com.data_arts_studio.web_mvp_back.shared.exception.ErrorCode;

/**
 * 테스트 런 기능에서 사용하는 비즈니스 에러 코드를 정의
 */
public enum TestRunErrorCode implements ErrorCode {
    TEST_RUN_INVALID("TR400001", "잘못된 테스트 런 요청입니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    TestRunErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
