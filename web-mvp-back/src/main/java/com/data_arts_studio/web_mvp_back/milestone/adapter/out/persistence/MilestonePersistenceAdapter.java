package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.MilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.MilestoneJpaRepository;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.mapper.MilestoneMapper;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.CheckMilestoneNamePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.CheckProjectExistsPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;
import com.data_arts_studio.web_mvp_back.project.adapter.out.persistence.jpa.ProjectJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 저장, 로드와 프로젝트 및 이름 검증을 담당하는 영속성 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestonePersistenceAdapter implements SaveMilestonePort,
        LoadMilestonePort,
        CheckProjectExistsPort,
        CheckMilestoneNamePort {

    private final MilestoneJpaRepository milestoneJpaRepository;
    private final ProjectJpaRepository projectJpaRepository;
    private final MilestoneMapper milestoneMapper;

    @Override
    /**
     * 마일스톤 신규 레코드를 저장
     *
     * @param milestone 저장할 마일스톤 도메인 객체
     */
    public void createMilestone(Milestone milestone) {
        MilestoneJpaEntity entity = milestoneMapper.toJpaEntity(milestone);
        milestoneJpaRepository.save(entity);
    }

    @Override
    /**
     * 마일스톤 변경 내용을 갱신 저장
     *
     * @param milestone 갱신할 마일스톤 도메인 객체
     */
    public void updateMilestone(Milestone milestone) {
        MilestoneJpaEntity entity = milestoneMapper.toJpaEntity(milestone);
        milestoneJpaRepository.save(entity);
    }

    @Override
    /**
     * 아카이브되지 않은 마일스톤만 식별자로 조회 
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 조회 결과
     */
    public Optional<Milestone> loadById(MilestoneId milestoneId) {
        return milestoneJpaRepository.findByIdAndArchivedAtIsNull(UUID.fromString(milestoneId.getId()))
                .map(milestoneMapper::toDomain);
    }

    @Override
    /**
     * 활성 프로젝트 존재 여부를 확인
     *
     * @param projectId 프로젝트 식별자
     * @return 존재 여부
     */
    public boolean existsById(String projectId) {
        return projectJpaRepository.existsByArchivedAtIsNullAndId(UUID.fromString(projectId));
    }

    @Override
    /**
     * 같은 프로젝트 내 활성 마일스톤 이름 중복 여부를 확인 
     *
     * @param projectId 프로젝트 식별자
     * @param name 마일스톤 이름
     * @return 중복 여부
     */
    public boolean isMilestoneNameDuplicated(String projectId, String name) {
        return milestoneJpaRepository.existsByProjectIdAndNameAndArchivedAtIsNull(UUID.fromString(projectId), name);
    }

    @Override
    /**
     * 특정 마일스톤을 제외하고 같은 프로젝트 내 이름 중복 여부를 확인 
     *
     * @param projectId 프로젝트 식별자
     * @param name 마일스톤 이름
     * @param excludeMilestoneId 제외할 마일스톤 식별자
     * @return 중복 여부
     */
    public boolean isMilestoneNameDuplicatedExceptId(String projectId, String name, String excludeMilestoneId) {
        return milestoneJpaRepository.existsByProjectIdAndNameAndIdNotAndArchivedAtIsNull(
                UUID.fromString(projectId),
                name,
                UUID.fromString(excludeMilestoneId));
    }
}
