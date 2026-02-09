package com.data_arts_studio.web_mvp_back.test_case.application.validator;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseCommand;

@Component
public class TestCaseValidator {

    public void validate(CreateTestCaseCommand command) {
        validateName(command.name());
    }

    private void validateName(String name) {
        // 이름 빈 값 검증
        if (name == null || name.isBlank()) {
            throw new TestCaseBusinessException(TestCaseErrorCode.TESTCASE_NAME_EMPTY_VALUE);
        }
    }
}
