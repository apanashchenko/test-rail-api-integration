package com.test.rail.api.test.classes;

import com.test.rail.api.annotation.DefectId;
import com.test.rail.api.annotation.TestCaseId;

/**
 * Created by alpa on 10/25/17
 */
public class TestCaseIdSingleAnnotations {

    @TestCaseId(id = 10)
    @DefectId("BUG-1")
    public void example() {

    }
}
