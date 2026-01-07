package com.data_arts_studio.web_mvp_back.test_suite.application.validator;

import com.data_arts_studio.web_mvp_back.shared.exception.BaseException;

public class TestSuiteBusinessException extends BaseException {
    
    // enum ErrorCode 기반 예외 생성
    public TestSuiteBusinessException(TestSuiteErrorCode errorCode) {
        super(errorCode);
    }
}
