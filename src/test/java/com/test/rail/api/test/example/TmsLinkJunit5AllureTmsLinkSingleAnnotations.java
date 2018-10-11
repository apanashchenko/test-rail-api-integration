package com.test.rail.api.test.example;

import com.test.rail.api.listeners.junit5.TestRailJunit5AllureListener;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({TestRailJunit5AllureListener.class})
public class TmsLinkJunit5AllureTmsLinkSingleAnnotations {

    @TmsLink("1")
    public void example(){

    }

}
