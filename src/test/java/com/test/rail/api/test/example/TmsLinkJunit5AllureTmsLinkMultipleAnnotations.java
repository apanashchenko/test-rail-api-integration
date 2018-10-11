package com.test.rail.api.test.example;

import com.test.rail.api.listeners.junit5.TestRailJunit5AllureListener;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({TestRailJunit5AllureListener.class})
public class TmsLinkJunit5AllureTmsLinkMultipleAnnotations {

    @TmsLinks({
            @TmsLink("1"),
            @TmsLink("2"),
            @TmsLink("3")
    })
    public void example(){

    }

}

