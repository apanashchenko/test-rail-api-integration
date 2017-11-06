package com.test.rail.integration.junit.listener;

import com.test.rail.integration.junit.impl.ListenerService;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Created by alpa on 10/30/17
 */
@RunListener.ThreadSafe
public class TestRailJUnitListener extends RunListener {

    public void testRunStarted(Description description) throws Exception {
        ListenerService.getInstance().createTestRun(description);
    }

    public void testRunFinished(Result result) throws Exception {

    }

    public void testStarted(Description description) throws Exception {

    }

    public void testFinished(Description description) throws Exception {
        ListenerService.getInstance().sendResultToTestRail(description, null);
    }

    public void testFailure(Failure failure) throws Exception {
        ListenerService.getInstance().sendResultToTestRail(failure.getDescription(), failure.getException());
    }

    public void testAssumptionFailure(Failure failure) {
        ListenerService.getInstance().sendResultToTestRail(failure.getDescription(), failure.getException());
    }

    public void testIgnored(Description description) throws Exception {
        ListenerService.getInstance().sendResultToTestRail(description, null);
    }

}
