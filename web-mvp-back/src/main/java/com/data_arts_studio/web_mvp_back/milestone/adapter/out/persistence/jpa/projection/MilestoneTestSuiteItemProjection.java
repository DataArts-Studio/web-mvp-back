package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.projection;

import java.util.UUID;

/**
 * 마일스톤 테스트 스위트 목록 조회용 projection
 */
public interface MilestoneTestSuiteItemProjection {
    UUID getId();

    String getName();

    String getDescription();

    Long getLinkedTestCaseCount();
}
