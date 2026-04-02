package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.entity.MilestoneJpaEntity;
import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository.MilestoneJpaRepository;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneStatisticsQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneDetailResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetProjectMilestoneItemResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.MilestoneStatisticsResult;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 조회 전용 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneQueryAdapter implements MilestoneQueryPort {

    private final MilestoneJpaRepository milestoneJpaRepository;
    private final MilestoneStatisticsQueryPort milestoneStatisticsQueryPort;

    /**
     * 프로젝트에 속한 활성 마일스톤 목록 조회
     *
     * @param projectId 프로젝트 식별자
     * @return 프로젝트 마일스톤 목록
     */
    @Override
    public List<GetProjectMilestoneItemResult> findAllByProject(String projectId) {
        List<MilestoneJpaEntity> milestones = milestoneJpaRepository
                .findAllByProjectIdAndArchivedAtIsNullOrderByStartDateAscCreatedAtDesc(UUID.fromString(projectId));
        // TODO(refactor): MVP 이후 목록/상세 공통 조회 조립이 더 커지면 기본 마일스톤 로드와 응답 매핑 책임을 별도 read model 조립기로 분리할 것
        Map<String, MilestoneStatisticsResult> statisticsByMilestoneId = milestoneStatisticsQueryPort.findStatisticsByMilestoneIds(
                milestones.stream().map(m -> m.getId().toString()).toList());

        return milestones.stream()
                .map(milestone -> toProjectMilestoneItemResult(
                        milestone,
                        statisticsByMilestoneId.getOrDefault(
                                milestone.getId().toString(),
                                MilestoneStatisticsResult.empty(milestone.getId().toString()))))
                .toList();
    }

    /**
     * 마일스톤 상세 정보와 통계 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 마일스톤 상세 조회 결과
     */
    @Override
    public Optional<GetMilestoneDetailResult> findDetail(String milestoneId) {
        return milestoneJpaRepository.findByIdAndArchivedAtIsNull(UUID.fromString(milestoneId))
                .map(milestone -> toMilestoneDetailResult(
                        milestone,
                        milestoneStatisticsQueryPort.findStatisticsByMilestoneIds(List.of(milestoneId))
                                .getOrDefault(milestoneId, MilestoneStatisticsResult.empty(milestoneId))));
    }

    /**
     * 마일스톤 엔티티와 통계 정보를 프로젝트 목록용 조회 모델로 변환
     *
     * @param milestone 마일스톤 엔티티
     * @param statistics 마일스톤 통계 정보
     * @return 프로젝트 마일스톤 목록 아이템
     */
    private GetProjectMilestoneItemResult toProjectMilestoneItemResult(MilestoneJpaEntity milestone,
                                                                       MilestoneStatisticsResult statistics) {
        return GetProjectMilestoneItemResult.builder()
                .id(milestone.getId().toString())
                .projectId(milestone.getProjectId().toString())
                .name(milestone.getName())
                .descriptionPreview(truncateDescription(milestone.getDescription()))
                .status(milestone.getProgressStatus().getDbValue())
                .startDate(toOffsetDateTime(milestone.getStartDate()))
                .endDate(toOffsetDateTime(milestone.getEndDate()))
                .progressPercent(statistics.progressPercent())
                .totalCaseCount(statistics.totalCaseCount())
                .completedCaseCount(statistics.completedCaseCount())
                .testRunCount(statistics.testRunCount())
                .build();
    }

    /**
     * 마일스톤 엔티티와 통계 정보를 상세 조회용 결과 모델로 변환
     *
     * @param milestone 마일스톤 엔티티
     * @param statistics 마일스톤 통계 정보
     * @return 마일스톤 상세 조회 결과
     */
    private GetMilestoneDetailResult toMilestoneDetailResult(MilestoneJpaEntity milestone,
                                                             MilestoneStatisticsResult statistics) {
        return GetMilestoneDetailResult.builder()
                .id(milestone.getId().toString())
                .projectId(milestone.getProjectId().toString())
                .name(milestone.getName())
                .description(milestone.getDescription())
                .status(milestone.getProgressStatus().getDbValue())
                .startDate(toOffsetDateTime(milestone.getStartDate()))
                .endDate(toOffsetDateTime(milestone.getEndDate()))
                .progressPercent(statistics.progressPercent())
                .completedCaseCount(statistics.completedCaseCount())
                .totalCaseCount(statistics.totalCaseCount())
                .testRunCount(statistics.testRunCount())
                .createdAt(milestone.getCreatedAt())
                .updatedAt(milestone.getUpdatedAt())
                .testCases(List.of())
                .testSuites(List.of())
                .testRuns(List.of())
                .build();
    }

    /**
     * 긴 설명 문자열을 목록 화면에 맞는 길이로 축약
     *
     * @param description 원본 설명
     * @return 축약된 설명 문자열
     */
    private String truncateDescription(String description) {
        if (description == null || description.isBlank()) {
            return "";
        }
        return description.length() > 80 ? description.substring(0, 80) + "..." : description;
    }

    /**
     * DB의 날짜 값을 자정 UTC 기준 OffsetDateTime으로 변환
     *
     * @param date DB에서 조회한 날짜 값
     * @return UTC 기준 OffsetDateTime
     */
    private OffsetDateTime toOffsetDateTime(LocalDate date) {
        return date == null ? null : date.atStartOfDay().atOffset(ZoneOffset.UTC);
    }
}
