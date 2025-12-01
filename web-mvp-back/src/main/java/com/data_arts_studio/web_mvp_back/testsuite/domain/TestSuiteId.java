package com.data_arts_studio.web_mvp_back.testsuite.domain;

import com.data_arts_studio.web_mvp_back.shared.Identifier;

public class TestSuiteId extends Identifier {

    public TestSuiteId(String id) {
        super(id);
    }

    public static TestSuiteId create() {
        return new TestSuiteId(Identifier.newId());
    }
    
}
