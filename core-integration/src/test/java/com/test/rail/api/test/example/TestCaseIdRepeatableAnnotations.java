package com.test.rail.api.test.example;

import com.test.rail.api.annotation.DefectId;
import com.test.rail.api.annotation.TestCaseId;

/**
 * Created by alpa on 10/25/17
 */
public class TestCaseIdRepeatableAnnotations {

    @TestCaseId(id = 2)
    @TestCaseId(id = 3)
    @DefectId("BUG-2")
    @DefectId("BUG-3")
    public void example() {

    }

}
