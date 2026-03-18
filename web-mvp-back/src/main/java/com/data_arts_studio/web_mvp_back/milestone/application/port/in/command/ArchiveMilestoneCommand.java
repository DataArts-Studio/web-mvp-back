package com.data_arts_studio.web_mvp_back.milestone.application.port.in.command;

/**
 * 마일스톤 아카이브 요청을 표현하는 커맨드
 *
 * @param milestoneId 아카이브할 마일스톤 식별자
 */
public record ArchiveMilestoneCommand(
    String milestoneId) {
}
