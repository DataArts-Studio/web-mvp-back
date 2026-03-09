package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.mapper;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.MilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneProgressStatus;
import com.data_arts_studio.web_mvp_back.project.domain.ProjectId;

@Component
public class MilestoneMapper {

    // 도메인은 String 기반 식별자 VO를 쓰고, JPA는 DB uuid 컬럼에 맞춰 UUID를 쓴다.
    public MilestoneJpaEntity toJpaEntity(Milestone milestone) {
        return new MilestoneJpaEntity(
                UUID.fromString(milestone.getId().getId()),
                UUID.fromString(milestone.getProjectId().getId()),
                milestone.getName(),
                milestone.getDescription(),
                milestone.getStatus().name(),
                toLocalDate(milestone.getStartDate()),
                toLocalDate(milestone.getEndDate()),
                milestone.getCreatedAt(),
                milestone.getUpdatedAt(),
                milestone.getArchivedAt(),
                milestone.getLifecycleStatus());
    }

    // JPA 엔티티를 도메인으로 복원하면서 UUID/날짜 표현 차이를 다시 도메인 규칙에 맞춤
    public Milestone toDomain(MilestoneJpaEntity entity) {
        Milestone milestone = new Milestone(
                new MilestoneId(entity.getId().toString()),
                new ProjectId(entity.getProjectId().toString()),
                entity.getName(),
                entity.getDescription(),
                toOffsetDateTime(entity.getStartDate()),
                toOffsetDateTime(entity.getEndDate()),
                MilestoneProgressStatus.valueOf(entity.getProgressStatus()));
        milestone.restoreAuditFields(entity.getCreatedAt(), entity.getUpdatedAt(), entity.getArchivedAt());
        // BaseEntity 상태를 DB 값에 맞게 다시 맞춤
        if (entity.getLifecycleStatus() != milestone.getLifecycleStatus()) {
            if (entity.getArchivedAt() == null) {
                milestone.restore();
            } else {
                milestone.markArchived();
                milestone.restoreAuditFields(entity.getCreatedAt(), entity.getUpdatedAt(), entity.getArchivedAt());
            }
        }
        return milestone;
    }
// TODO: milestones.start_date/end_date를 timestamptz로 바꾸면 이 변환은 제거 가능

    // DB는 date, 도메인은 OffsetDateTime을 사용하므로 저장 시 날짜만
    private java.time.LocalDate toLocalDate(OffsetDateTime dateTime) {
        return dateTime == null ? null : dateTime.toLocalDate();
    }

    // DB의 date를 도메인으로 올릴 때는 시간 정보를 자정 UTC로 보정해 채움
    private OffsetDateTime toOffsetDateTime(java.time.LocalDate date) {
        return date == null ? null : date.atTime(LocalTime.MIDNIGHT).atOffset(ZoneOffset.UTC);
    }
}
