package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.MilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

/**
 * 마일스톤 도메인과 JPA 엔티티 사이의 표현 차이를 정리하는 매퍼
 */
@Component
public class MilestoneMapper {

    /**
     * 도메인 마일스톤을 JPA 엔티티로 변환
     * 도메인 식별자 VO는 내부 String payload를 가지므로 UUID 컬럼 저장 전에 변환 필요
     *
     * @param milestone 도메인 마일스톤
     * @return JPA 엔티티
     */
    public MilestoneJpaEntity toJpaEntity(Milestone milestone) {
        return new MilestoneJpaEntity(
                UUID.fromString(milestone.getId().getId()),
                UUID.fromString(milestone.getProjectId().getId()),
                milestone.getName(),
                milestone.getDescription(),
                milestone.getStatus(),
                toLocalDate(milestone.getStartDate()),
                toLocalDate(milestone.getEndDate()),
                milestone.getCreatedAt(),
                milestone.getUpdatedAt(),
                milestone.getArchivedAt(),
                milestone.getLifecycleStatus());
    }

    /**
     * JPA 엔티티를 도메인 마일스톤으로 복원
     * DB의 date 컬럼과 UUID 컬럼 표현을 도메인 타입으로 되돌리고 BaseEntity 상태도 함께 복원
     *
     * @param entity JPA 엔티티
     * @return 도메인 마일스톤
     */
    public Milestone toDomain(MilestoneJpaEntity entity) {
        Milestone milestone = new Milestone(
                new MilestoneId(entity.getId().toString()),
                new ProjectId(entity.getProjectId().toString()),
                entity.getName(),
                entity.getDescription(),
                toOffsetDateTime(entity.getStartDate()),
                toOffsetDateTime(entity.getEndDate()),
                entity.getProgressStatus());
        milestone.restoreAuditFields(entity.getCreatedAt(), entity.getUpdatedAt(), entity.getArchivedAt());
        if (entity.getArchivedAt() != null) {
            // BaseEntity 기본 상태 대신 DB의 archived 상태 복원
            milestone.archive();
            milestone.restoreAuditFields(entity.getCreatedAt(), entity.getUpdatedAt(), entity.getArchivedAt());
        }
        return milestone;
    }

    // DB는 date 컬럼을 사용하므로 OffsetDateTime에서 날짜 부분만 저장
    private LocalDate toLocalDate(OffsetDateTime dateTime) {
        return dateTime == null ? null : dateTime.toLocalDate();
    }

    // DB의 date를 도메인 OffsetDateTime으로 복원할 때는 자정 UTC를 기준 시각으로 채움
    private OffsetDateTime toOffsetDateTime(LocalDate date) {
        return date == null ? null : date.atTime(LocalTime.MIDNIGHT).atOffset(ZoneOffset.UTC);
    }
}
