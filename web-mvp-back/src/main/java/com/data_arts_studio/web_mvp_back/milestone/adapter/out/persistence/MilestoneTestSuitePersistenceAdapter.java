package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneTestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id.MilestoneTestSuiteJpaId;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository.MilestoneJpaRepository;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository.MilestoneTestSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.AssignTestSuiteToMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestoneTestSuiteLinksPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.RemoveTestSuiteFromMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.ReplaceMilestoneTestSuitesPort;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.entity.TestSuiteJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.repository.TestSuiteJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤-테스트스위트 링크를 저장하는 영속성 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneTestSuitePersistenceAdapter implements AssignTestSuiteToMilestonePort,
                                                             ReplaceMilestoneTestSuitesPort,
                                                             RemoveTestSuiteFromMilestonePort,
                                                             LoadMilestoneTestSuiteLinksPort {

    private final MilestoneTestSuiteJpaRepository milestoneTestSuiteJpaRepository;
    private final MilestoneJpaRepository milestoneJpaRepository;
    private final TestSuiteJpaRepository testSuiteJpaRepository;


    @Override
    /**
     * 마일스톤에 테스트 스위트 링크 한 건 저장
     *
     * @param milestoneId 마일스톤 식별자
     * @param testSuiteId 테스트 스위트 식별자
     */
    public void assign(String milestoneId, String testSuiteId) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        UUID testSuiteUuid = UUID.fromString(testSuiteId);
        MilestoneJpaEntity milestone = loadActiveMilestone(milestoneUuid);
        TestSuiteJpaEntity testSuite = loadTestSuiteInProject(milestone.getProjectId(), testSuiteUuid);

        MilestoneTestSuiteJpaId id = new MilestoneTestSuiteJpaId(milestoneUuid, testSuiteUuid);
        if (milestoneTestSuiteJpaRepository.existsById(id)) {
            // 같은 링크 재요청은 멱등하게 처리
            return;
        }
        milestoneTestSuiteJpaRepository.save(new MilestoneTestSuiteJpaEntity(milestone, testSuite));
    }

    @Override
    /**
     * 마일스톤에 연결된 테스트 스위트 집합 전체 교체
     *
     * @param milestoneId 마일스톤 식별자
     * @param testSuiteIds 최종 테스트 스위트 식별자 목록
     */
    public void replace(String milestoneId, List<String> testSuiteIds) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        MilestoneJpaEntity milestone = loadActiveMilestone(milestoneUuid);
        Set<String> uniqueIds = new LinkedHashSet<>(testSuiteIds == null ? List.of() : testSuiteIds);

        milestoneTestSuiteJpaRepository.deleteByIdMilestoneId(milestoneUuid);
        List<MilestoneTestSuiteJpaEntity> entities = uniqueIds.stream()
                .map(UUID::fromString)
                .map(testSuiteUuid -> new MilestoneTestSuiteJpaEntity(
                        milestone,
                        loadTestSuiteInProject(milestone.getProjectId(), testSuiteUuid)))
                .toList();
        milestoneTestSuiteJpaRepository.saveAll(entities);
    }

    @Override
    /**
     * 마일스톤과 테스트 스위트 사이의 링크 한 건 제거
     *
     * @param milestoneId 마일스톤 식별자
     * @param testSuiteId 테스트 스위트 식별자
     */
    public void remove(String milestoneId, String testSuiteId) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        UUID testSuiteUuid = UUID.fromString(testSuiteId);
        MilestoneJpaEntity milestone = loadActiveMilestone(milestoneUuid);
        loadTestSuiteInProject(milestone.getProjectId(), testSuiteUuid);
        milestoneTestSuiteJpaRepository.deleteByIdMilestoneIdAndIdTestSuiteId(milestoneUuid, testSuiteUuid);
    }

    @Override
    /**
     * 마일스톤에 연결된 테스트 스위트 식별자 목록 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 식별자 목록
     */
    public List<String> loadTestSuiteIdsByMilestone(String milestoneId) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        loadActiveMilestone(milestoneUuid);
        // EmbeddedId 내부의 testSuiteId만 꺼내서 응용 계층에서 바로 사용할 수 있게 변환
        return milestoneTestSuiteJpaRepository.findAllByIdMilestoneId(milestoneUuid).stream()
                .map(link -> link.getId().getTestSuiteId().toString())
                .toList();
    }

    /**
     * 활성 마일스톤 존재 여부 확인 후 소속 프로젝트 ID 반환
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤이 속한 프로젝트 식별자
     */
    private MilestoneJpaEntity loadActiveMilestone(UUID milestoneId) {
        return milestoneJpaRepository.findByIdAndArchivedAtIsNull(milestoneId)
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
    }

    /**
     * 테스트 스위트 존재 여부와 같은 프로젝트 소속인지 검증
     *
     * @param projectId 프로젝트 식별자
     * @param testSuiteId 테스트 스위트 식별자
     */
    private TestSuiteJpaEntity loadTestSuiteInProject(UUID projectId, UUID testSuiteId) {
        TestSuiteJpaEntity testSuite = testSuiteJpaRepository.findById(testSuiteId)
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.TEST_SUITE_NOT_FOUND));
        if (!projectId.equals(testSuite.getProjectId())) {
            // 같은 프로젝트 소속 스위트만 범위에 넣도록 보장
            throw new MilestoneBusinessException(MilestoneErrorCode.TEST_SUITE_OUT_OF_PROJECT);
        }
        return testSuite;
    }
}
