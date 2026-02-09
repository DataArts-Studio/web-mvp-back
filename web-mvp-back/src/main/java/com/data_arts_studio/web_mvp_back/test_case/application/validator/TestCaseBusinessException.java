package com.data_arts_studio.web_mvp_back.test_case.application.validator;

import com.data_arts_studio.web_mvp_back.shared.exception.BaseException;
import com.data_arts_studio.web_mvp_back.shared.exception.ErrorCode;

public class TestCaseBusinessException extends BaseException{
    // enum ErrorCode 기반 예외 생성
    protected TestCaseBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
