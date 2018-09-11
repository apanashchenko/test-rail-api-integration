package com.test.rail.api.test.example;

import com.test.rail.api.annotation.DefectId;
import com.test.rail.api.annotation.DefectIds;
import com.test.rail.api.annotation.TestCaseId;
import com.test.rail.api.annotation.TestCaseIds;

/**
 * Created by alpa on 10/25/17
 */
public class TestCaseIdsMultipleAnnotations {

    @TestCaseIds({
            @TestCaseId(id = 5),
            @TestCaseId(id = 6),
            @TestCaseId(id = 7)
    })
    @DefectIds({
            @DefectId("BUG-4"),
            @DefectId("BUG-5"),
            @DefectId("BUG-6"),
    })
    public void example() {

    }
}
