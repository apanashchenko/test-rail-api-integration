package com.test.rail.integration.junit.rule;

import com.test.rail.integration.junit.impl.ListenerService;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by alpa on 10/31/17
 */

public class TestRailRule extends TestWatcher {

    protected void starting(Description description) {
        ListenerService.getInstance().createTestRun(description);
    }

    protected void succeeded(Description description) {
        ListenerService.getInstance().sendResultToTestRail(description, null);

    }

    protected void failed(Throwable e, Description description) {
        ListenerService.getInstance().sendResultToTestRail(description, e);
    }

    protected void skipped(AssumptionViolatedException e, Description description) {
        ListenerService.getInstance().sendResultToTestRail(description, e);
    }


    protected void finished(Description description) {

    }

}
