package com.data_arts_studio.web_mvp_back.test_case.application.validator;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_case.application.port.in.CreateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.UpdateTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseBusinessException;
import com.data_arts_studio.web_mvp_back.test_case.application.exception.TestCaseErrorCode;

@Component
public class TestCaseValidator {

    // 테스트 케이스 생성 커맨드 검증
    public void validate(CreateTestCaseCommand command) {
        validateName(command.name());
    }
    // 테스트 케이스 수정 커맨드 검증
    public void validate(UpdateTestCaseCommand command) {
        validateName(command.name());
    }


    // 이름 검증
    private void validateName(String name) {
        // 이름 빈 값 검증
        if (name == null || name.isBlank()) {
            throw new TestCaseBusinessException(TestCaseErrorCode.TESTCASE_NAME_EMPTY_VALUE);
        }
    }
}
