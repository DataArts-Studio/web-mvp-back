package com.data_arts_studio.web_mvp_back.test_case.application.service;

import org.springframework.stereotype.Service;

import com.data_arts_studio.web_mvp_back.test_case.application.port.in.command.ArchiveTestCaseCommand;
import com.data_arts_studio.web_mvp_back.test_case.application.port.in.usecase.ArchiveTestCaseUseCase;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.ArchiveTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.application.port.out.LoadTestCasePort;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;
import com.data_arts_studio.web_mvp_back.test_case.domain.TestCaseId;
import lombok.RequiredArgsConstructor;

/**
 * 테스트 케이스 아카이브 유스케이스 구현 서비스.
 * 요청 커맨드로 대상 테스트 케이스를 조회하고, 도메인 아카이브 처리 후 저장
 */
@Service
@RequiredArgsConstructor
public class ArchiveTestCaseService implements ArchiveTestCaseUseCase {
   
   private final LoadTestCasePort loadTestCasePort;
   private final ArchiveTestCasePort archiveTestCasePort;

   @Override
   public void archiveTestCase(ArchiveTestCaseCommand command) {
      // TODO(authz): command.projectId()와 실제 testCase.projectId가 일치하는지 검증하고, 추후 호출자 권한도 함께 확인할 것.
      TestCase testCase = loadTestCasePort.loadTestCase(new TestCaseId(command.testCaseId()))
                  // TODO: 추 후 커스텀된 에러 메세지 입력
                  .orElseThrow(() -> new IllegalArgumentException("테스트 케이스를 찾을 수 없습니다."));
      testCase.archive();
      archiveTestCasePort.archive(testCase);
   }
    
}
