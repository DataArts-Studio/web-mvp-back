package com.data_arts_studio.web_mvp_back.test_case.application.validator;

import org.springframework.http.HttpStatus;
import com.data_arts_studio.web_mvp_back.shared.exception.ErrorCode;

/**
 * 테스트 케이스의 모든 비즈니스 예외 코드와 HTTP 상태 코드를 매핑하는 Enum 클래스 
 */
public enum TestCaseErrorCode implements ErrorCode{
    // 테스트 케이스 이름이 비어있음
    TESTCASE_NAME_EMPTY_VALUE("TC40098", "테스트 케이스 제목을 입력해주세요.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;   

    TestCaseErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
