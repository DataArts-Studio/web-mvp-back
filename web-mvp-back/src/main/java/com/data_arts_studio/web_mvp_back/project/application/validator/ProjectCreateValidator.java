package com.data_arts_studio.web_mvp_back.project.application.validator;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.application.port.in.CreateProjectCommand;
import com.data_arts_studio.web_mvp_back.project.application.port.out.CheckProjectNamePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectCreateValidator {

    private final CheckProjectNamePort checkProjectNamePort;

    public void validate(CreateProjectCommand command) {
            validateName(command.name());
            validatePassword(command.password(), command.passwordConfirm());
        }

        private void validateName(String name) {
            // 이름 빈 값 검증
            if (name == null || name.isEmpty()) {
                throw new ProjectBusinessException(ProjectErrorCode.PROJECT_NAME_EMPTY_VALUE);
            }
            // 이름 길이 검증
            if (name.length() > 50) {
                throw new ProjectBusinessException(ProjectErrorCode.PROJECT_NAME_LENGTH_EXCEEDED);
            }
            // 이름 중복 검증
            if (checkProjectNamePort.isProjectNameDuplicated(name)) {
                throw new ProjectBusinessException(ProjectErrorCode.PROJECT_NAME_DUPLICATED);
            }
        }

        private void validatePassword(String password, String passwordConfirm) {
            if (password == null || password.isEmpty()) {
                throw new ProjectBusinessException(ProjectErrorCode.PROJECT_PASSWORD_EMPTY_VALUE);
            }
            if (password.length() < 4) {
                throw new ProjectBusinessException(ProjectErrorCode.PROJECT_PASSWORD_TOO_SHORT);
            }
            if (password.equals(passwordConfirm)) {
                throw new ProjectBusinessException(ProjectErrorCode.PROJECT_PASSWORD_MISMATCH);
            }
        }
     
    }