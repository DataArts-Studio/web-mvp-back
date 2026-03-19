package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneJpaEntity;

public interface MilestoneJpaRepository extends JpaRepository<MilestoneJpaEntity, UUID> {
    // 아카이브되지 않은 마일스톤을 식별자로 조회
    Optional<MilestoneJpaEntity> findByIdAndArchivedAtIsNull(UUID id);

    // 특정 프로젝트의 활성 마일스톤 목록을 시작일/생성일 기준으로 정렬 조회
    List<MilestoneJpaEntity> findAllByProjectIdAndArchivedAtIsNullOrderByStartDateAscCreatedAtDesc(UUID projectId);

    // 같은 프로젝트 내 활성 마일스톤 이름 중복 여부 확인
    boolean existsByProjectIdAndNameAndArchivedAtIsNull(UUID projectId, String name);

    // 특정 마일스톤을 제외한 같은 프로젝트 내 활성 이름 중복 여부 확인
    boolean existsByProjectIdAndNameAndIdNotAndArchivedAtIsNull(UUID projectId, String name, UUID id);
}
