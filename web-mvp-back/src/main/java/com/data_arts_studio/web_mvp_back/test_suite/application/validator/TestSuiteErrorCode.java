package com.data_arts_studio.web_mvp_back.test_suite.application.validator;

import org.springframework.http.HttpStatus;

import com.data_arts_studio.web_mvp_back.shared.exception.ErrorCode;

public enum TestSuiteErrorCode implements ErrorCode{
    // 문서번호: FDD-MVP-001: 테스트 스위트 생성 관련 예외 (Prefix: TS4XX)
    // 테스트 스위트 이름 빈 값(유효 값) 예외
    TEST_SUITE_NAME_EMPTY_VALUE("TS40098", "유효한 이름을 입력해주세요.", HttpStatus.BAD_REQUEST),
    // 테스트 스위트 이름 길이 초과 예외
    TEST_SUITE_NAME_LENGTH_EXCEEDED("TS40097", "스위트 이름은 50자를 초과할 수 없습니다.", HttpStatus.BAD_REQUEST),
    // 테스트 스위트 이름 길이 부족 예외
    TEST_SUITE_NAME_TOO_SHORT("TS40096", "스위트 이름은 최소 5자 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    // 테스트 스위트 이름에 특수 문자 입력 예외
    TEST_SUITE_NAME_INVALID_CHARACTERS("TS40095", "특수문자는 사용할 수 없습니다.", HttpStatus.BAD_REQUEST),

    // 테스트 스위트에 대한 프로젝트를 찾을 수 없는 예외
    PROJECT_NOT_FOUND("TS404001", "해당 프로젝트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 테스트 스위트 중복 예외 
    TEST_SUITE_NAME_DUPLICATED("TS409001", "이미 존재하는 테스트 스위트 이름입니다.", HttpStatus.CONFLICT),

    CREATE_TEST_SUITE_NO_PERMISSION("TS403001", "테스트 스위트를 생성할 권한이 없습니다.", HttpStatus.FORBIDDEN);

    // TS5XX 
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    TestSuiteErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
