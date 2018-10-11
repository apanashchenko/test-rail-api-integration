package com.test.rail.api.test.example;

import com.test.rail.api.annotation.TestCaseId;
import com.test.rail.api.annotation.TestCaseIds;
import com.test.rail.api.listeners.junit5.TestRailJunit5Listener;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({TestRailJunit5Listener.class})
public class TestCaseIdJunit5MultipleAnnotation {

    @TestCaseIds({
            @TestCaseId(id = 5),
            @TestCaseId(id = 6),
            @TestCaseId(id = 7)
    })
    public void example(){

    }

}
