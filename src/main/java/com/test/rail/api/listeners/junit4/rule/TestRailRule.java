package com.test.rail.api.listeners.junit4.rule;

import com.test.rail.api.listeners.junit4.ListenerService;
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
