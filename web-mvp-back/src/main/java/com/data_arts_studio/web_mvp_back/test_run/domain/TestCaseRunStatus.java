package com.data_arts_studio.web_mvp_back.test_run.domain;

/**
 * 테스트 런에 포함된 테스트 케이스 실행 결과 상태
 */
public enum TestCaseRunStatus {
    UNTESTED("untested"),
    PASS("pass"),
    FAIL("fail"),
    BLOCKED("blocked");

    private final String dbValue;

    TestCaseRunStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static TestCaseRunStatus fromDbValue(String dbValue) {
        for (TestCaseRunStatus status : values()) {
            if (status.dbValue.equals(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("알 수 없는 TestCaseRunStatus 값입니다: " + dbValue);
    }
}
