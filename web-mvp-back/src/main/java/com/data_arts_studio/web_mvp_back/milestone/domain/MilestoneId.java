package com.data_arts_studio.web_mvp_back.milestone.domain;

import com.data_arts_studio.web_mvp_back.shared.Identifier;

public class MilestoneId extends Identifier {

    public MilestoneId(String id) {
        super(id);
    }

    public static MilestoneId create() {
        return new MilestoneId(Identifier.newId());
    }
    
}
