package com.data_arts_studio.web_mvp_back.shared.exception;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.data_arts_studio.web_mvp_back.project.application.validator.ProjectBusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectBusinessException.class)
    public ResponseEntity<?> handleProjectBusinessException(ProjectBusinessException ex) {

        var body = new HashMap<String, Object>();
        body.put("timestamp", LocalDateTime.now());
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, ex.getHttpStatus());
    }
}