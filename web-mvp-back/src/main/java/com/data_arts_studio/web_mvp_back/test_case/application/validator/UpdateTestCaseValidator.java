package com.data_arts_studio.web_mvp_back.test_case.application.validator;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseBusinessException;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseErrorCode;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.UpdateTestCaseCommand;

@Component
public class UpdateTestCaseValidator {

    public void validate(UpdateTestCaseCommand command) {
        validateName(command.name());
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new TestCaseBusinessException(TestCaseErrorCode.TESTCASE_NAME_EMPTY_VALUE);
        }
    }
}
