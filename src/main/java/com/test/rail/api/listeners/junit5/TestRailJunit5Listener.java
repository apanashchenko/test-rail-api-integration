package com.test.rail.api.listeners.junit5;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestRailJunit5Listener implements AfterTestExecutionCallback, BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        ListenerService.getInstance().createTestRun(context);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        ListenerService.getInstance().sendResultToTestRail(context);
    }

}
