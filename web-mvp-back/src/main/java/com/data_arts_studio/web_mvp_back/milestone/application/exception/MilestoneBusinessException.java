package com.data_arts_studio.web_mvp_back.milestone.application.exception;

import com.data_arts_studio.web_mvp_back.shared.exception.BaseException;

/**
 * 마일스톤 기능에서 사용하는 비즈니스 예외
 */
public class MilestoneBusinessException extends BaseException {

    public MilestoneBusinessException(MilestoneErrorCode errorCode) {
        super(errorCode);
    }
}
