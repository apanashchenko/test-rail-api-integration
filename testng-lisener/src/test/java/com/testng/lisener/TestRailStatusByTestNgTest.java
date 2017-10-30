package com.testng.lisener;

import com.test.rail.api.ITestRailResultStatus;
import com.test.rail.api.integration.testng.lisener.impl.TestRailStatusITestNgImpl;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import static org.testng.Assert.assertEquals;

/**
 * Created by alpa on 10/26/17
 */
public class TestRailStatusByTestNgTest {

    @Test
    public void successResultTest() {
        assertEquals(new TestRailStatusITestNgImpl().getStatus(TestResult.SUCCESS), ITestRailResultStatus.SUCCESS);
    }

    @Test
    public void failureResultTest() {
        assertEquals(new TestRailStatusITestNgImpl().getStatus(TestResult.FAILURE), ITestRailResultStatus.FAILURE);
    }

    @Test
    public void rejectedResultTest() {
        assertEquals(new TestRailStatusITestNgImpl().getStatus(TestResult.SKIP), ITestRailResultStatus.REJECTED);
    }
}
