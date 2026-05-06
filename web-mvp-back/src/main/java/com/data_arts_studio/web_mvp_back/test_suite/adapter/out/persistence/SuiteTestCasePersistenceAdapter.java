package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.jpa.TestCaseJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.entity.SuiteTestCaseJpaEntity;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.repository.SuiteTestCaseJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.repository.TestSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.AssignTestCaseToSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.LoadSuiteTestCaseLinksPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.RemoveTestCaseFromSuitePort;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.ReplaceSuiteTestCasesPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/**
 * 스위트-테스트케이스 링크 영속 어댑터
 *
 * 링크 단건 추가, 구성 전체 교체, 단건 해제, 링크 조회를 수행하며 프로젝트 범위 검증을 함께 처리
 */
@Component
@RequiredArgsConstructor
public class SuiteTestCasePersistenceAdapter implements AssignTestCaseToSuitePort,
                                                        ReplaceSuiteTestCasesPort,
                                                        RemoveTestCaseFromSuitePort,
                                                        LoadSuiteTestCaseLinksPort {

    private final SuiteTestCaseJpaRepository suiteTestCaseJpaRepository;
    private final TestSuiteJpaRepository testSuiteJpaRepository;
    private final TestCaseJpaRepository testCaseJpaRepository;
    private final EntityManager entityManager;

    /**
     * 스위트에 테스트케이스 링크 1건을 추가
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    @Override
    public void assign(String projectId, String suiteId, String testCaseId) {
        UUID suiteUuid = UUID.fromString(suiteId);
        UUID testCaseUuid = UUID.fromString(testCaseId);
        validateSuiteInProject(projectId, suiteId);
        validateTestCaseInProject(projectId, testCaseId);

        if (suiteTestCaseJpaRepository.existsBySuiteIdAndTestCaseId(suiteUuid, testCaseUuid)) {
            // 똑같은 케이스가 들어오면 멱등하게 처리함
            return;
        }

        suiteTestCaseJpaRepository.save(new SuiteTestCaseJpaEntity(UUID.randomUUID(), suiteUuid, testCaseUuid));
    }

    /**
     * 스위트 구성 링크를 전달된 테스트케이스 목록으로 전체 교체
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseIds 최종 연결할 테스트 케이스 식별자 목록
     */
    @Override
    public void replace(String projectId, String suiteId, List<String> testCaseIds) {
        UUID suiteUuid = UUID.fromString(suiteId);
        validateSuiteInProject(projectId, suiteId);

        Set<String> uniqueIds = new LinkedHashSet<>(testCaseIds == null ? List.<String>of() : testCaseIds);
        for (String testCaseId : uniqueIds) {
            validateTestCaseInProject(projectId, testCaseId);
        }

        suiteTestCaseJpaRepository.deleteBySuiteId(suiteUuid);
        List<SuiteTestCaseJpaEntity> links = uniqueIds.stream()
                .map(testCaseId -> new SuiteTestCaseJpaEntity(UUID.randomUUID(), suiteUuid, UUID.fromString(testCaseId)))
                .toList();
        suiteTestCaseJpaRepository.saveAll(links);
    }

    /**
     * 스위트-테스트케이스 링크 1건을 해제
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    @Override
    public void remove(String projectId, String suiteId, String testCaseId) {
        validateSuiteInProject(projectId, suiteId);
        validateTestCaseInProject(projectId, testCaseId);
        suiteTestCaseJpaRepository.deleteBySuiteIdAndTestCaseId(UUID.fromString(suiteId), UUID.fromString(testCaseId));
    }

    /**
     * 스위트에 연결된 테스트케이스 ID 목록을 조회
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 연결된 테스트 케이스 ID 목록
     */
    @Override
    public List<String> loadTestCaseIdsBySuite(String projectId, String suiteId) {
        validateSuiteInProject(projectId, suiteId);
        Query query = entityManager.createNativeQuery("""
                SELECT tc.id
                FROM suite_test_cases stc
                JOIN test_cases tc ON tc.id = stc.test_case_id
                WHERE stc.suite_id = :suiteId
                  AND tc.archived_at IS NULL
                ORDER BY tc.sort_order ASC, tc.created_at ASC
                """);
        query.setParameter("suiteId", UUID.fromString(suiteId));
        return query.getResultList().stream()
                .map(Object::toString)
                .toList();
    }

    /**
     * 지정 스위트가 해당 프로젝트에 속하는지 검증
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     */
    private void validateSuiteInProject(String projectId, String suiteId) {
        boolean exists = testSuiteJpaRepository.findByIdAndProjectId(UUID.fromString(suiteId), UUID.fromString(projectId)).isPresent();
        if (!exists) {
            // TODO(exception): IllegalArgumentException 대신 도메인 예외로 통일해 권한/존재 여부 응답 정책을 일관되게 맞출 것.
            throw new IllegalArgumentException("프로젝트 내 테스트 스위트를 찾을 수 없습니다.");
        }
    }

    /**
     * 지정 테스트케이스가 해당 프로젝트에 속하는지 검증
     *
     * @param projectId 프로젝트 식별자
     * @param testCaseId 테스트 케이스 식별자
     */
    private void validateTestCaseInProject(String projectId, String testCaseId) {
        TestCaseJpaEntity testCase = testCaseJpaRepository.findById(UUID.fromString(testCaseId))
                // TODO(exception): IllegalArgumentException 대신 도메인 예외로 통일해 권한/존재 여부 응답 정책을 일관되게 맞출 것.
                .orElseThrow(() -> new IllegalArgumentException("프로젝트 내 테스트 케이스를 찾을 수 없습니다."));
        if (!projectId.equals(testCase.getProjectId().toString())) {
            throw new IllegalArgumentException("프로젝트 내 테스트 케이스를 찾을 수 없습니다.");
        }
    }
}
