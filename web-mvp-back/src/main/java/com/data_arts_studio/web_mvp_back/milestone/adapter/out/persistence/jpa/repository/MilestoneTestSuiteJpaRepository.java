package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneTestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id.MilestoneTestSuiteJpaId;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.projection.MilestoneTestSuiteItemProjection;

public interface MilestoneTestSuiteJpaRepository extends JpaRepository<MilestoneTestSuiteJpaEntity, 
                                                                       MilestoneTestSuiteJpaId> {
    // 특정 마일스톤에 연결된 모든 테스트 스위트 매핑을 조회
    List<MilestoneTestSuiteJpaEntity> findAllByIdMilestoneId(UUID milestoneId);

    /**
     * 스위트 목록을 projection으로 조회
     * 마일스톤에 연결된 테스트 스위트 목록을 조회하는 커스텀 쿼리
     * 각 테스트 스위트에 연겶된 테스트 케이스 수를 함께 조회  
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 목록과 각 스위트에 연결된 테스트 케이스 수를 포함하는 projection 리스트
     * */                                                       
    @Query("""
            select mts.testSuite.id as id,
                   mts.testSuite.name as name,
                   mts.testSuite.description as description,
                   (
                       select count(stc.id)
                       from SuiteTestCaseJpaEntity stc
                       where stc.suiteId = mts.testSuite.id
                   ) as linkedTestCaseCount
            from MilestoneTestSuiteJpaEntity mts
            where mts.milestone.id = :milestoneId
              and mts.testSuite.archivedAt is null
            order by mts.testSuite.sortOrder asc, mts.testSuite.createdAt asc
            """)
    List<MilestoneTestSuiteItemProjection> findSuiteItemsByMilestoneId(UUID milestoneId);

    
    // 특정 마일스톤과 테스트 스위트 간의 연결 매핑 하나를 삭제
    void deleteByIdMilestoneIdAndIdTestSuiteId(UUID milestoneId, UUID testSuiteId);
}
