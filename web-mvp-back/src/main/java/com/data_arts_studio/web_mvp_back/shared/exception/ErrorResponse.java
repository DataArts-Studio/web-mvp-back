package com.data_arts_studio.web_mvp_back.shared.exception;

import java.time.LocalDateTime;

// 에러 응답 DTO 클래스 
public record ErrorResponse(String errorCode, String errorMessage, int status, LocalDateTime timestamp) {
    public static ErrorResponse of(String errorCode, String errorMessage, int status) {
        return new ErrorResponse(errorCode, errorMessage, status, LocalDateTime.now()); 
    }
}
