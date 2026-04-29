package com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;
import com.data_arts_studio.web_mvp_back.test_run.adapter.out.persistence.jpa.TestRunJpaEntity;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRun;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRunId;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRunStatus;

/**
 * 테스트 런 도메인과 JPA 엔티티 사이의 표현 차이를 정리하는 매퍼
 */
@Component
public class TestRunMapper {

    /**
     * 도메인 테스트 런을 JPA 엔티티로 변환
     *
     * @param testRun 도메인 테스트 런
     * @return JPA 엔티티
     */
    public TestRunJpaEntity toJpaEntity(TestRun testRun) {
        return new TestRunJpaEntity(
                UUID.fromString(testRun.getId().getId()),
                UUID.fromString(testRun.getProjectId().getId()),
                testRun.getName(),
                testRun.getDescription(),
                testRun.getStatus().getDbValue(),
                testRun.getCreatedAt(),
                testRun.getUpdatedAt(),
                testRun.getArchivedAt(),
                testRun.getLifecycleStatus(),
                testRun.getShareToken(),
                toLocalDateTime(testRun.getShareExpiresAt()));
    }

    /**
     * JPA 엔티티를 도메인 테스트 런으로 복원
     *
     * @param entity 테스트 런 JPA 엔티티
     * @return 도메인 테스트 런
     */
    public TestRun toDomain(TestRunJpaEntity entity) {
        return new TestRun(
                new TestRunId(entity.getId().toString()),
                new ProjectId(entity.getProjectId().toString()),
                entity.getName(),
                entity.getDescription(),
                TestRunStatus.fromDbValue(entity.getStatus()),
                entity.getLifecycleStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getArchivedAt(),
                entity.getShareToken(),
                toOffsetDateTime(entity.getShareExpiresAt()),
                null);
    }

    /**
     * timezone 정보가 없는 DB datetime 값을 UTC 기준 offset datetime으로 복원
     *
     * @param value DB의 local datetime 값
     * @return UTC 기준 offset datetime
     */
    private OffsetDateTime toOffsetDateTime(LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    /**
     * 도메인 offset datetime 값을 DB 저장용 local datetime으로 변환
     *
     * @param value 도메인 offset datetime 값
     * @return DB 저장용 local datetime
     */
    private LocalDateTime toLocalDateTime(OffsetDateTime value) {
        return value == null ? null : value.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
}
