package com.data_arts_studio.web_mvp_back.test_run.domain;

import com.data_arts_studio.web_mvp_back.shared.Identifier;

public class TestRunId extends Identifier {

    public TestRunId(String id) {
        super(id);
    }

    public static TestRunId create() {
        return new TestRunId(Identifier.newId());
    }
    
}
