package com.data_arts_studio.web_mvp_back.test_run.application.loader;

import java.util.List;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneBusinessException;
import com.data_arts_studio.web_mvp_back.milestone.application.exception.MilestoneErrorCode;
import com.data_arts_studio.web_mvp_back.milestone.application.port.out.LoadMilestonePort;
import com.data_arts_studio.web_mvp_back.milestone.domain.Milestone;
import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneId;

import lombok.RequiredArgsConstructor;

/**
 * 테스트 런 생성에 사용할 마일스톤을 로드하는 컴포넌트
 */
@Component
@RequiredArgsConstructor
public class TestRunMilestoneLoader {

    private final LoadMilestonePort loadMilestonePort;

    /**
     * 식별자 목록으로 마일스톤을 로드하고, 하나라도 없으면 비즈니스 예외를 발생
     *
     * @param milestoneIds 선택된 마일스톤 식별자 목록
     * @return 로드된 마일스톤 목록
     */
    public List<Milestone> load(List<String> milestoneIds) {
        return milestoneIds.stream()
                .map(this::loadSingle)
                .toList();
    }

    /**
     * 식별자 하나로 마일스톤을 로드하고, 없으면 비즈니스 예외를 발생
     *
     * @param milestoneId 선택된 마일스톤 식별자
     * @return 로드된 마일스톤
     */
    private Milestone loadSingle(String milestoneId) {
        return loadMilestonePort.loadById(new MilestoneId(milestoneId))
                .orElseThrow(() -> new MilestoneBusinessException(MilestoneErrorCode.MILESTONE_NOT_FOUND));
    }
}
