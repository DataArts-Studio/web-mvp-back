package com.data_arts_studio.web_mvp_back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.data_arts_studio.web_mvp_back.project.application.port.out.LoadProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.port.out.SaveProjectPort;
import com.data_arts_studio.web_mvp_back.project.application.validator.ProjectCreateValidator;

@SpringBootTest
class WebMvpBackApplicationTests {
    @MockBean
    private SaveProjectPort saveProjectPort;

    @MockBean
    private LoadProjectPort loadProjectPort; // 추가된 모킹

    @MockBean
    private ProjectCreateValidator projectCreateValidator;

    @MockBean
    private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
	}

}
