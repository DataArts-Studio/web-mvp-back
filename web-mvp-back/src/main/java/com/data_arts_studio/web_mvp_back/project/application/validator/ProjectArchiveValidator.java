package com.data_arts_studio.web_mvp_back.project.application.validator;

import org.springframework.stereotype.Component;

import com.data_arts_studio.web_mvp_back.project.application.exception.ProjectBusinessException;
import com.data_arts_studio.web_mvp_back.project.application.exception.ProjectErrorCode;
import com.data_arts_studio.web_mvp_back.project.application.port.in.ArchiveProjectCommand;
import com.data_arts_studio.web_mvp_back.project.domain.Project;
import com.data_arts_studio.web_mvp_back.shared.LifecycleStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectArchiveValidator {

    /**
     * 아카이브 요청 유효성 검증
     * @param project DB에서 조회한 실제 Project Entity
     * @param command 사용자 요청 커맨드 
     */
    public void validate(Project project, ArchiveProjectCommand command) {
        validateStatus(project);
        validateNameMatch(project, command.confirmName());
    }
    // 이미 아카이브 되었거나 없는 상태인지 확인 
    private void validateStatus(Project project) {
        if (project.getLifecycleStatus() == LifecycleStatus.ARCHIVED) {
            throw new ProjectBusinessException(ProjectErrorCode.PROJECT_ALREADY_ARCHIVED);
        }
    }
    // 실제로 있는 프로젝트 이름과 같은지 확인
    private void validateNameMatch(Project project, String confirmName) {
        if (confirmName == null || !project.getName().equals(confirmName)) {
            throw new ProjectBusinessException(ProjectErrorCode.PROJECT_ARCHIVE_NAME_MISMATCH);
        }
    }
}
