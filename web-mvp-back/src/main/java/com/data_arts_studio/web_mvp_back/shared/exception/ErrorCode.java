package com.data_arts_studio.web_mvp_back.shared.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getErrorCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
