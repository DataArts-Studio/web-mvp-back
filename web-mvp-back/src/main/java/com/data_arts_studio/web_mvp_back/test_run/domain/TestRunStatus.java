package com.data_arts_studio.web_mvp_back.test_run.domain;

/**
 * 테스트 런 상태를 표현하는 enum이다.
 */
public enum TestRunStatus {
    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String dbValue;

    TestRunStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * DB에 저장하는 상태 코드값을 반환한다.
     *
     * @return DB 상태 코드값
     */
    public String getDbValue() {
        return dbValue;
    }

    /**
     * DB 상태 코드값을 도메인 enum으로 복원한다.
     *
     * @param dbValue DB 상태 코드값
     * @return 복원된 테스트 런 상태
     */
    public static TestRunStatus fromDbValue(String dbValue) {
        for (TestRunStatus status : values()) {
            if (status.dbValue.equals(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("알 수 없는 TestRunStatus 값입니다: " + dbValue);
    }
}
