package com.data_arts_studio.web_mvp_back.test_run.application.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.test_run.application.service.result.CreateTestRunResult;
import com.data_arts_studio.web_mvp_back.test_run.domain.TestRun;

/**
 * 테스트 런 도메인 상태를 애플리케이션 결과 모델로 변환
 */
@Component
public class TestRunResultMapper {

    /**
     * 저장된 테스트 런 도메인 상태를 생성 결과 모델로 변환
     *
     * @param testRun 저장된 테스트 런 도메인 모델
     * @param milestoneIds 테스트 런에 연결된 마일스톤 식별자 목록
     * @return 테스트 런 생성 결과
     */
    public CreateTestRunResult toCreateTestRunResult(TestRun testRun, List<String> milestoneIds) {
        return CreateTestRunResult.builder()
                .id(testRun.getId().getId())
                .projectId(testRun.getProjectId().getId())
                .milestoneIds(List.copyOf(milestoneIds))
                .name(testRun.getName())
                .description(testRun.getDescription())
                .status(testRun.getStatus().getDbValue())
                .createdAt(testRun.getCreatedAt())
                .updatedAt(testRun.getUpdatedAt())
                .archivedAt(testRun.getArchivedAt())
                .lifecycleStatus(testRun.getLifecycleStatus().name())
                .shareToken(testRun.getShareToken())
                .shareExpiresAt(testRun.getShareExpiresAt())
                // .shareAiSummary(testRun.getShareAiSummary())
                .build();
    }
}
