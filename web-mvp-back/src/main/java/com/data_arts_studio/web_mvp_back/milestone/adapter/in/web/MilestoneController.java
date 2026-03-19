package com.data_arts_studio.web_mvp_back.milestone.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request.CreateMilestoneRequest;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request.CreateMilestoneTestRunRequest;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.request.UpdateMilestoneRequest;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.CreateMilestoneResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.CreateMilestoneTestRunResponse;
import com.data_arts_studio.web_mvp_back.milestone.adapter.in.web.response.UpdateMilestoneResponse;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.ArchiveMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.CreateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.CreateMilestoneTestRunCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.command.UpdateMilestoneCommand;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.ArchiveMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.AssignTestCaseToMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.AssignTestSuiteToMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.CreateMilestoneTestRunUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.CreateMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestCaseLinksUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestRunsUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneTestSuiteLinksUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.QueryMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.RemoveTestCaseFromMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.RemoveTestSuiteFromMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.ReplaceMilestoneTestCasesUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.port.in.usecase.UpdateMilestoneUseCase;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.CreateMilestoneResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.CreateMilestoneTestRunResult;
import com.data_arts_studio.web_mvp_back.milestone.application.service.result.UpdateMilestoneResult;
import lombok.RequiredArgsConstructor;

/**
 * 마일스톤 CRUD, 범위 관리, 테스트 실행 연동 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MilestoneController {
    private final CreateMilestoneUseCase createMilestoneUseCase;
    private final UpdateMilestoneUseCase updateMilestoneUseCase;
    private final ArchiveMilestoneUseCase archiveMilestoneUseCase;

    private final CreateMilestoneTestRunUseCase createMilestoneTestRunUseCase;

    /**
     * 특정 프로젝트에 마일스톤을 생성
     *
     * @param projectId 프로젝트 식별자
     * @param request 생성 요청 DTO
     * @return 생성된 마일스톤 응답
     */
    @PostMapping("/projects/{projectId}/milestones")
    public ResponseEntity<CreateMilestoneResponse> createMilestone(@PathVariable String projectId,
                                                                   @RequestBody CreateMilestoneRequest request) {
        CreateMilestoneCommand command = CreateMilestoneCommand.builder()
                .projectId(projectId)
                .name(request.name())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
        CreateMilestoneResult result = createMilestoneUseCase.createMilestone(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateMilestoneResponse(
                result.id(),
                result.projectId(),
                result.name(),
                result.description(),
                result.status(),
                result.startDate(),
                result.endDate(),
                result.createdAt()));
    }

    /**
     * 마일스톤 기본 정보를 수정
     *
     * @param milestoneId 마일스톤 식별자
     * @param request 수정 요청 DTO
     * @return 수정된 마일스톤 응답
     */
    @PatchMapping("/milestones/{milestoneId}")
    public ResponseEntity<UpdateMilestoneResponse> updateMilestone(@PathVariable String milestoneId,
                                                                   @RequestBody UpdateMilestoneRequest request) {
        // TODO(validation): 기능 안정화 후 요청 DTO에 Bean Validation과 @Valid를 적용해 1차 입력 검증을 컨트롤러에서 처리할 것.
        UpdateMilestoneCommand command = UpdateMilestoneCommand.builder()
                .milestoneId(milestoneId)
                .name(request.name())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
        UpdateMilestoneResult result = updateMilestoneUseCase.updateMilestone(command);
        return ResponseEntity.ok(new UpdateMilestoneResponse(
                result.id(),
                result.projectId(),
                result.name(),
                result.description(),
                result.status(),
                result.startDate(),
                result.endDate(),
                result.updatedAt()));
    }

    /**
     * 마일스톤 아카이브
     *
     * @param milestoneId 마일스톤 식별자
     * @return no content 응답
     */
    @DeleteMapping("/milestones/{milestoneId}")
    public ResponseEntity<Void> archiveMilestone(@PathVariable String milestoneId) {
        archiveMilestoneUseCase.archiveMilestone(new ArchiveMilestoneCommand(milestoneId));
        return ResponseEntity.noContent().build();
    }

     /**
     * 마일스톤 기반 테스트 실행을 생성
     *
     * @param request 테스트 실행 생성 요청 DTO
     * @return 생성된 테스트 실행 응답
     */
    @PostMapping("/test-runs")
    public ResponseEntity<CreateMilestoneTestRunResponse> createMilestoneTestRun(@RequestBody CreateMilestoneTestRunRequest request) {
        CreateMilestoneTestRunResult result = createMilestoneTestRunUseCase.createMilestoneTestRun(
                CreateMilestoneTestRunCommand.builder()
                        .projectId(request.projectId())
                        .milestoneId(request.milestoneId())
                        .name(request.name())
                        .description(request.description())
                        .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateMilestoneTestRunResponse(
                result.id(),
                result.projectId(),
                result.milestoneId(),
                result.name(),
                result.status()));
    }

   
}
