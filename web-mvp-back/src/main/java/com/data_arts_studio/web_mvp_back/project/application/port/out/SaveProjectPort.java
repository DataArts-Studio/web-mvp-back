package com.data_arts_studio.web_mvp_back.project.application.port.out;

import com.data_arts_studio.web_mvp_back.project.domain.Project;

public interface SaveProjectPort {
    void save(Project project);
}
