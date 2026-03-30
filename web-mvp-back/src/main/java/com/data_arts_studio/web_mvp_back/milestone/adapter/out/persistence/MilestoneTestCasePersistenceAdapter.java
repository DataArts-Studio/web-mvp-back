package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneTestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.id.MilestoneTestCaseJpaId;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository.MilestoneJpaRepository;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository.MilestoneTestCaseJpaRepository;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.AssignTestCaseToMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestoneTestCaseLinksPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.RemoveTestCaseFromMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.ReplaceMilestoneTestCasesPort;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤-테스트케이스 링크를 저장하는 영속성 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneTestCasePersistenceAdapter implements AssignTestCaseToMilestonePort,
        ReplaceMilestoneTestCasesPort,
        RemoveTestCaseFromMilestonePort,
        LoadMilestoneTestCaseLinksPort {

    private final MilestoneTestCaseJpaRepository milestoneTestCaseJpaRepository;
    private final MilestoneJpaRepository milestoneJpaRepository;
    private final TestCaseJpaRepository testCaseJpaRepository;

    @Override
    /**
     * 마일스톤에 테스트 케이스 링크 한 건 저장
     *
     * @param milestoneId 마일스톤 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    public void assign(String milestoneId, String testCaseId) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        UUID testCaseUuid = UUID.fromString(testCaseId);
        MilestoneJpaEntity milestone = loadActiveMilestone(milestoneUuid);
        TestCaseJpaEntity testCase = loadTestCaseInProject(milestone.getProjectId(), testCaseUuid);

        MilestoneTestCaseJpaId id = new MilestoneTestCaseJpaId(milestoneUuid, testCaseUuid);
        if (milestoneTestCaseJpaRepository.existsById(id)) {
            // 같은 링크 재요청은 멱등하게 처리
            return;
        }
        milestoneTestCaseJpaRepository.save(new MilestoneTestCaseJpaEntity(milestone, testCase));
    }

    @Override
    /**
     * 마일스톤에 연결된 테스트 케이스 집합 전체 교체
     *
     * @param milestoneId 마일스톤 식별자
     * @param testCaseIds 최종 테스트 케이스 식별자 목록
     */
    public void replace(String milestoneId, List<String> testCaseIds) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        MilestoneJpaEntity milestone = loadActiveMilestone(milestoneUuid);
        // LinkedHashSet으로 감싸서 중복 제거와 입력 순서 유지를 같이 처리
        Set<String> uniqueIds = new LinkedHashSet<>(testCaseIds == null ? List.of() : testCaseIds);

        // 전체 교체는 기존 링크를 지우고 최종 집합만 다시 저장
        milestoneTestCaseJpaRepository.deleteByIdMilestoneId(milestoneUuid);
        // 최종 식별자 목록을 링크 엔티티 목록으로 변환
        List<MilestoneTestCaseJpaEntity> entities = uniqueIds.stream()
                .map(UUID::fromString)
                .map(testCaseUuid -> new MilestoneTestCaseJpaEntity(
                        milestone,
                        loadTestCaseInProject(milestone.getProjectId(), testCaseUuid)))
                .toList();
        milestoneTestCaseJpaRepository.saveAll(entities);
    }

    @Override
    /**
     * 마일스톤과 테스트 케이스 사이의 링크 한 건 제거
     *
     * @param milestoneId 마일스톤 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    public void remove(String milestoneId, String testCaseId) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        UUID testCaseUuid = UUID.fromString(testCaseId);
        MilestoneJpaEntity milestone = loadActiveMilestone(milestoneUuid);
        loadTestCaseInProject(milestone.getProjectId(), testCaseUuid);
        milestoneTestCaseJpaRepository.deleteByIdMilestoneIdAndIdTestCaseId(milestoneUuid, testCaseUuid);
    }

    @Override
    /**
     * 마일스톤에 연결된 테스트 케이스 식별자 목록 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 케이스 식별자 목록
     */
    public List<String> loadTestCaseIdsByMilestone(String milestoneId) {
        UUID milestoneUuid = UUID.fromString(milestoneId);
        loadActiveMilestone(milestoneUuid);
        // EmbeddedId 내부의 testCaseId만 꺼내서 응용 계층이 바로 쓸 수 있는 문자열 목록으로 변환
        return milestoneTestCaseJpaRepository.findAllByIdMilestoneId(milestoneUuid).stream()
                .map(link -> link.getId().getTestCaseId().toString())
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
     * 테스트 케이스 존재 여부와 같은 프로젝트 소속인지 검증
     *
     * @param projectId 프로젝트 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    private TestCaseJpaEntity loadTestCaseInProject(UUID projectId, UUID testCaseId) {
        TestCaseJpaEntity testCase = testCaseJpaRepository.findById(testCaseId)
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.TEST_CASE_NOT_FOUND));
        if (!projectId.equals(testCase.getProjectId())) {
            // 같은 프로젝트 소속 케이스만 범위에 넣도록 보장
            throw new MilestoneBusinessException(MilestoneErrorCode.TEST_CASE_OUT_OF_PROJECT);
        }
        return testCase;
    }
}
