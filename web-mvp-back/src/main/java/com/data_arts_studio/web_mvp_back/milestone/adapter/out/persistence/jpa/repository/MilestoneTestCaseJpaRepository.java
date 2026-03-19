package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneTestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id.MilestoneTestCaseJpaId;

public interface MilestoneTestCaseJpaRepository extends JpaRepository<MilestoneTestCaseJpaEntity, 
                                                                      MilestoneTestCaseJpaId> {
    /** 특정 마일스톤에 연결된 모든 테스트 케이스 매핑을 조회 */
    List<MilestoneTestCaseJpaEntity> findAllByIdMilestoneId(UUID milestoneId);

    /** 특정 마일스톤에 연결된 테스트 케이스 개수를 조회 */
    long countByIdMilestoneId(UUID milestoneId);

    /** 특정 마일스톤에 연결된 테스트 케이스 매핑을 모두 제거*/
    void deleteByIdMilestoneId(UUID milestoneId);

    /** 특정 마일스톤과 테스트 케이스 간의 연결 매핑 하나를 제거 */
    void deleteByIdMilestoneIdAndIdTestCaseId(UUID milestoneId, UUID testCaseId);
}
