package com.data_arts_studio.web_mvp_back.test_suite.application.validator;

import org.springframework.stereotype.Component;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.in.CreateTestSuiteCommand;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.CheckProjectExistsPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.CheckTestSuiteNamePort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestSuiteCreateValidator {

    // 테스트스위트 이름 중복 검사용 포트
    private final CheckTestSuiteNamePort checkTestSuiteNamePort;
    // 프로젝트 존재 여부 검사용 포트
    private final CheckProjectExistsPort checkProjectExistsPort;
    // 테스트스위트 이름 형식 검사용 정규식 패턴
    private static final String TEST_SUITE_NAME_PATTERN = "^[a-zA-Z0-9가-힣._\\- ]+$";

    public void validate(CreateTestSuiteCommand command) {
        // 프로젝트 존재 여부 검증
        validateProjectExists(command.projectId());
        // 이름 형식 검증
        validateNameFormat(command.name());
        // 이름 중복 검증
        validateNameDuplication(command.projectId(), command.name());
    }
    
    // 프로젝트 존재 여부 검증
    private void validateProjectExists(String projectId) {
        if (!checkProjectExistsPort.existsById(projectId)) {
            throw new TestSuiteBusinessException(TestSuiteErrorCode.PROJECT_NOT_FOUND);
        }
    }

    private void validateNameFormat(String name) {
        // 공백 입력 검증 
        if (name == null || name.isBlank()) {
            throw new TestSuiteBusinessException(TestSuiteErrorCode.TEST_SUITE_NAME_EMPTY_VALUE);
        }
        // 길이 검증 
        // 최소 길이 5자 미만 입력 시
        if (name.length() < 5) {
            throw new  TestSuiteBusinessException(TestSuiteErrorCode.TEST_SUITE_NAME_TOO_SHORT);
        }
        // 50자 초과 입력 시
        if (name.length() > 50) {
            throw new TestSuiteBusinessException(TestSuiteErrorCode.TEST_SUITE_NAME_LENGTH_EXCEEDED);
        } 
        // 특수문자 입력 검증
        if (!name.matches(TEST_SUITE_NAME_PATTERN)) {
            throw new TestSuiteBusinessException(TestSuiteErrorCode.TEST_SUITE_NAME_INVALID_CHARACTERS);
        }

    }

    private void validateNameDuplication(String projectId, String name) {
        // 같은 프로젝트 내에 이름 중복 검증
        if (checkTestSuiteNamePort.isTestSuiteNameDuplicated(projectId, name)) {
            throw new TestSuiteBusinessException(TestSuiteErrorCode.TEST_SUITE_NAME_DUPLICATED);
        }

    }
    
}
