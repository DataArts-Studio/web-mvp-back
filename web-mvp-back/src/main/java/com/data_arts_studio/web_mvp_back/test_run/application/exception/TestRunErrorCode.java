package com.data_arts_studio.web_mvp_back.test_run.application.exception;

import org.springframework.http.HttpStatus;

import com.data_arts_studio.web_mvp_back.shared.exception.ErrorCode;

/**
 * 테스트 런 기능에서 사용하는 비즈니스 에러 코드를 정의
 */
public enum TestRunErrorCode implements ErrorCode {
    TEST_RUN_INVALID("TR400001", "잘못된 테스트 런 요청입니다.", HttpStatus.BAD_REQUEST),
    TEST_RUN_NAME_EMPTY("TR400002", "테스트 실행 이름을 입력해주세요.", HttpStatus.BAD_REQUEST),
    TEST_RUN_MILESTONES_EMPTY("TR400009", "테스트 실행에 사용할 마일스톤을 선택해주세요.", HttpStatus.BAD_REQUEST),
    TEST_RUN_MILESTONES_DUPLICATED("TR400010", "동일한 마일스톤을 중복 선택할 수 없습니다.", HttpStatus.BAD_REQUEST);

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
