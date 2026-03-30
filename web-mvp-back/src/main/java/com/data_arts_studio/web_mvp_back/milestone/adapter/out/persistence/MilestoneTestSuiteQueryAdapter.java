package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.repository.MilestoneTestSuiteJpaRepository;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.MilestoneTestSuiteQueryPort;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.GetMilestoneTestSuiteItemResult;

import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 테스트 스위트 조회 전용 read model 어댑터
 */
@Component
@RequiredArgsConstructor
public class MilestoneTestSuiteQueryAdapter implements MilestoneTestSuiteQueryPort {
    private final MilestoneTestSuiteJpaRepository milestoneTestSuiteJpaRepository;

    @Override
    /**
     * 특정 마일스톤에 연결된 테스트 스위트 목록을 조회
     *
     * @param milestoneId 마일스톤 식별자
     * @return 테스트 스위트 목록 조회 결과
     */
    public List<GetMilestoneTestSuiteItemResult> findTestSuites(String milestoneId) {
        return milestoneTestSuiteJpaRepository.findSuiteItemsByMilestoneId(UUID.fromString(milestoneId)).stream()
                .map(item -> GetMilestoneTestSuiteItemResult.builder()
                        .id(item.getId().toString())
                        .name(item.getName())
                        .description(item.getDescription())
                        .linkedTestCaseCount(item.getLinkedTestCaseCount().intValue())
                        .build())
                .toList();
    }
}
