package com.data_arts_studio.web_mvp_back.project.domain;

import com.data_arts_studio.web_mvp_back.shared.Identifier;

public class ProjectId extends Identifier {

    public ProjectId(String id) {
        super(id);
    }

    public static ProjectId create() {
        return new ProjectId(Identifier.newId());
    }
    
}

