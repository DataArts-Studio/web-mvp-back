package com.data_arts_studio.web_mvp_back.test_result.domain;

import com.data_arts_studio.web_mvp_back.shared.Identifier;

public class TestResultId extends Identifier{
    
    public TestResultId(String id) {
        super(id);
    }

    public static TestResultId create() {
        return new TestResultId(Identifier.newId());
    }
}
