package com.data_arts_studio.web_mvp_back.test_case.domain;

import com.data_arts_studio.web_mvp_back.shared.Identifier;

public class TestCaseId extends Identifier {

    public TestCaseId(String id) {
        super(id);
    }

    public static TestCaseId create() {
        return new TestCaseId(Identifier.newId());
    }
}
