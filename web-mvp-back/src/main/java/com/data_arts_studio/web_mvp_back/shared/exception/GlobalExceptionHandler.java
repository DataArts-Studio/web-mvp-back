package com.data_arts_studio.web_mvp_back.shared.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 전역의 비지니스 관련 예외처리 핸들러
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
            ex.getErrorCode(),
            ex.getMessage(),
            ex.getHttpStatus().value()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    // 서버의 예상치 못한 예외처리 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            "SERVER_ERROR",
            "서버와 통신이 원활하지 않습니다.",
            500,
            java.time.LocalDateTime.now()
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}