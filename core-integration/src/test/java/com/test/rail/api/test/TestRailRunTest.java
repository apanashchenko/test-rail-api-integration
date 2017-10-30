package com.test.rail.api.test;

import com.test.rail.api.ITestRailRun;
import com.test.rail.api.impl.TestRailRunImpl;
import com.test.rail.api.models.Entry;
import com.test.rail.api.models.Run;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by alpa on 10/29/17
 */
public class TestRailRunTest {

    private  ITestRailRun testRailRun;

    @BeforeClass
    public void setUp() throws Exception {
        testRailRun = Whitebox.invokeConstructor(TestRailRunImpl.class);
    }

    @Test
    public void createTestRunEntryWithCustomTestCasesTest() throws Exception {
        int masterSuiteId = 1;
        List<Integer> testCases = Arrays.asList(1,2,3);
        Entry runEntry = testRailRun.createTestRunEntry(masterSuiteId, testCases);

        assertThat(runEntry.getName(), containsString("Automation test run"));
        assertThat(runEntry.getDescription(), equalTo("Automation test run"));
        assertThat(runEntry.getIncludeAll(), is(false));
        assertThat(runEntry.getSuiteId(), is(1));
        assertThat(runEntry.getCaseIds(), is(testCases));
    }

    @Test
    public void createTestRunEntryWithAllTestCasesTest() throws Exception {
        int masterSuiteId = 1;
        Entry runEntry = testRailRun.createTestRunEntry(masterSuiteId);

        assertThat(runEntry.getName(), containsString("Automation test run"));
        assertThat(runEntry.getDescription(), equalTo("Automation test run"));
        assertThat(runEntry.getIncludeAll(), is(true));
        assertThat(runEntry.getSuiteId(), is(1));
    }

    @Test
    public void createTestRunWithCustomTestCasesTest() throws Exception {
        int masterSuiteId = 1;
        List<Integer> testCases = Arrays.asList(1,2,3);
        Run run = testRailRun.createTestRun(masterSuiteId, testCases);

        assertThat(run.getName(), containsString("Automation test run"));
        assertThat(run.getDescription(), equalTo("Automation test run"));
        assertThat(run.getIncludeAll(), is(false));
        assertThat(run.getSuiteId(), is(1));
        assertThat(run.getCaseIds(), is(testCases));
    }

    @Test
    public void createTestRunWithAllTestCasesTest() throws Exception {
        int masterSuiteId = 1;
        Run run = testRailRun.createTestRun(masterSuiteId);

        assertThat(run.getName(), containsString("Automation test run"));
        assertThat(run.getDescription(), equalTo("Automation test run"));
        assertThat(run.getIncludeAll(), is(true));
        assertThat(run.getSuiteId(), is(1));
    }
}
