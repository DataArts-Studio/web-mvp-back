package com.data_arts_studio.web_mvp_back.test_run.application.updater;

import java.util.List;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.application.port.out.SaveMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneProgressStatus;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 런 생성에 따른 마일스톤 진행 상태 변경을 담당하는 컴포넌트
 */
@Component
@RequiredArgsConstructor
public class TestRunMilestoneProgressUpdater {

    private final SaveMilestonePort saveMilestonePort;

    /**
     * 테스트 런 생성에 사용한 마일스톤들 중 planned 상태를 inProgress 로 변경 후 저장
     *
     * @param milestones 생성에 사용한 마일스톤 목록
     */
    public void markPlannedMilestonesInProgress(List<Milestone> milestones) {
        for (Milestone milestone : milestones) {
            // 아직 시작 전인 마일스톤만 실행 진행 중으로 올리고, 이미 진행 중이거나 완료된 마일스톤은 그대로 
            if (milestone.getStatus() == MilestoneProgressStatus.PLANNED) {
                milestone.markInProgress();
                saveMilestonePort.updateMilestone(milestone);
            }
        }
    }
}
