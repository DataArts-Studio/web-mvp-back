package com.data_arts_studio.web_mvp_back.milestone.domain;

/**
 * 마일스톤 진행 상태를 표현하는 enum 클래스
 */
public enum MilestoneProgressStatus {
    PLANNED("planned"),
    IN_PROGRESS("inProgress"),
    DONE("done");

    private final String dbValue;

    MilestoneProgressStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * DB에 저장하는 상태 코드값을 반환
     *
     * @return DB 상태 코드값
     */
    public String getDbValue() {
        return dbValue;
    }

    /**
     * DB 상태 코드값을 도메인 enum으로 복원
     *
     * @param dbValue DB 상태 코드값
     * @return 복원된 enum 값
     */
    public static MilestoneProgressStatus fromDbValue(String dbValue) {
        for (MilestoneProgressStatus status : values()) {
            if (status.dbValue.equals(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("알 수 없는 MilestoneProgressStatus 값입니다: " + dbValue);
    }
}
