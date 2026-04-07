package com.data_arts_studio.web_mvp_back.test_run.application.exception;

import com.data_arts_studio.web_mvp_back.shared.exception.BaseException;

/**
 * 테스트 런 기능에서 사용하는 비지니스 예외
 */
public class TestRunBusinessException extends BaseException {

    public TestRunBusinessException(TestRunErrorCode errorCode) {
        super(errorCode);
    }
}
