package com.data_arts_studio.web_mvp_back.test_case.application.exception;

import com.data_arts_studio.web_mvp_back.shared.exception.BaseException;

public class TestCaseBusinessException extends BaseException{
    // enum ErrorCode 기반 예외 생성
    public TestCaseBusinessException(TestCaseErrorCode errorCode) {
        super(errorCode);
    }
}
