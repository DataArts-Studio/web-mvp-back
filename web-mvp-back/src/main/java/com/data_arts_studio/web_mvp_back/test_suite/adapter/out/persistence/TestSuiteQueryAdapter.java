package com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_suite.adapter.out.persistence.jpa.TestSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.test_suite.application.port.out.TestSuiteQueryPort;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetProjectTestSuiteItemResult;
import com.data_arts_studio.web_mvp_back.test_suite.application.service.result.GetTestSuiteDetailResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
/**
 * 테스트 스위트 조회 전용 영속성 어댑터.
 *
 * 애플리케이션의 조회 포트를 구현하며, JPA 리포지토리 조회 결과를 조회 전용 Result 모델로 변환함
 * 일단은 테스트 실행/케이스 집계 데이터가 없어 기본값으로 채움
 */
public class TestSuiteQueryAdapter implements TestSuiteQueryPort {

    private final TestSuiteJpaRepository testSuiteJpaRepository;

    /**
     * 프로젝트 범위 내 단일 테스트 스위트 상세 정보를 조회
     *
     * 대상 스위트가 존재하면 상세 Result 모델로 매핑하여 반환
     * 존재하지 않으면  Optional#empty()를 반환
     *
     * @param projectId 프로젝트 식별자
     * @param suiteId 테스트 스위트 식별자
     * @return 테스트 스위트 상세 조회 결과
     */
    @Override
    public Optional<GetTestSuiteDetailResult> findDetail(String projectId, String suiteId) {
        return testSuiteJpaRepository.findByIdAndProjectId(UUID.fromString(suiteId), UUID.fromString(projectId))
            .map(e -> GetTestSuiteDetailResult.builder()
                .suiteId(e.getId().toString())
                .name(e.getName())
                .description(e.getDescription() == null ? "" : e.getDescription())
                .createdAt(e.getCreatedAt())

                // MVP: 아직 테이블 없으니 기본값
                .testCaseCount(0)
                .executionCount(0)
                .lastPassRate(0.0)
                .build()
            );
    }

    /**
     * 프로젝트에 속한 테스트 스위트 목록을 정렬 순서대로 조회
     *
     * 지금은 유형/실행/마일스톤 관련 정보가 확정되지 않아 기본값(고정 문자열, 0, null)으로 채워 반환
     * @param projectId 프로젝트 식별자
     * @return 프로젝트 테스트 스위트 목록
     */
    @Override
    public List<GetProjectTestSuiteItemResult> findAllByProject(String projectId) {
        return testSuiteJpaRepository.findAllByProjectIdOrderBySortOrderAsc(UUID.fromString(projectId)).stream()
            .map(e -> GetProjectTestSuiteItemResult.builder()
                .suiteId(e.getId().toString())
                .name(e.getName())
                .type("기본") // MVP: 고정 or 엔티티 필드 있으면 연결
                .testCaseCount(0)
                .milestoneName(null)
                .lastExecutedAt(null)
                .executionCount(0)
                .build()
            )
            .toList();
    }
}
