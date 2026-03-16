package com.data_arts_studio.web_mvp_back.test_case.application.port.out;

import com.data_arts_studio.web_mvp_back.test_case.domain.TestCase;

/**
 * 아카이브된 테스트 케이스를 복원하는 유스케이스.
 */
public interface RestoreTestCasePort {
    /**
     * 지정한 테스트 케이스를 복원한다.
     *
     * @param command 복원 대상 테스트 케이스 식별자를 담은 커맨드
     */
    void restore(TestCase testCase);
}
