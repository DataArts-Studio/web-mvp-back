package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneTestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id.MilestoneTestSuiteJpaId;

public interface MilestoneTestSuiteJpaRepository extends JpaRepository<MilestoneTestSuiteJpaEntity, 
                                                                       MilestoneTestSuiteJpaId> {
    // 특정 마일스톤에 연결된 모든 테스트 스위트 매핑을 조회
    List<MilestoneTestSuiteJpaEntity> findAllByIdMilestoneId(UUID milestoneId);

    // 특정 마일스톤과 테스트 스위트 간의 연결 매핑 하나를 삭제
    void deleteByIdMilestoneIdAndIdTestSuiteId(UUID milestoneId, UUID testSuiteId);
}
