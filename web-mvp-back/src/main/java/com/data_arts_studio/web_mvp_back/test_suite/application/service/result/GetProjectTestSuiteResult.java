package com.data_arts_studio.web_mvp_back.test_suite.application.service.result;

import java.util.List;
import lombok.Builder;

@Builder
public record GetProjectTestSuiteResult(
    List<GetProjectTestSuiteItemResult> items
) {
    
}
