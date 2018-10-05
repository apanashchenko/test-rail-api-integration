package com.test.rail.api.test.example;

import com.test.rail.api.annotation.TestCaseId;
import com.test.rail.api.listeners.junit5.TestRailJunit5Listener;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({TestRailJunit5Listener.class})
public class TestCaseIdJunit5SingleAnnotation {

    @TestCaseId(id=1)
    public void example(){

    }

}
