package com.data_arts_studio.web_mvp_back.milestone.application.exception;

import org.springframework.http.HttpStatus;

import com.data_arts_studio.web_mvp_back.shared.exception.ErrorCode;

/**
 * 마일스톤 기능에서 사용하는 비즈니스 에러 코드를 정의
 */
public enum MilestoneErrorCode implements ErrorCode {
    PROJECT_NOT_FOUND("MS404001", "해당 프로젝트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MILESTONE_NOT_FOUND("MS404002", "해당 마일스톤을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MILESTONE_NAME_EMPTY("MS400001", "마일스톤 이름을 입력해주세요.", HttpStatus.BAD_REQUEST),
    MILESTONE_NAME_TOO_LONG("MS400002", "마일스톤 이름은 50자를 초과할 수 없습니다.", HttpStatus.BAD_REQUEST),
    MILESTONE_DESCRIPTION_TOO_LONG("MS400003", "마일스톤 설명은 500자를 초과할 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE("MS400004", "마일스톤 시작일은 종료일보다 늦을 수 없습니다.", HttpStatus.BAD_REQUEST),
    MILESTONE_NAME_DUPLICATED("MS409001", "이미 존재하는 마일스톤 이름입니다.", HttpStatus.CONFLICT),
    TEST_CASE_NOT_FOUND("MS404003", "해당 테스트 케이스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TEST_CASE_OUT_OF_PROJECT("MS400005", "같은 프로젝트의 테스트 케이스만 마일스톤에 추가할 수 있습니다.", HttpStatus.BAD_REQUEST),
    TEST_SUITE_NOT_FOUND("MS404004", "해당 테스트 스위트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TEST_SUITE_OUT_OF_PROJECT("MS400007", "같은 프로젝트의 테스트 스위트만 마일스톤에 추가할 수 있습니다.", HttpStatus.BAD_REQUEST),
    EMPTY_SCOPE_FOR_TEST_RUN("MS400006", "마일스톤에 포함된 테스트 케이스가 없어 테스트 실행을 생성할 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    MilestoneErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
