package com.test.rail.api.test.classes;

import com.test.rail.api.annotation.DefectId;
import com.test.rail.api.annotation.DefectIds;
import com.test.rail.api.annotation.TestCaseId;
import com.test.rail.api.annotation.TestCaseIds;

/**
 * Created by alpa on 10/25/17
 */
public class TestCaseIdsSingleAnnotations {

    @TestCaseIds(@TestCaseId(id = 4))
    @DefectIds(@DefectId("BUG-8"))
    public void example() {

    }
}
