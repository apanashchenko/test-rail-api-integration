package com.test.rail.api;

import com.test.rail.api.models.Entry;
import com.test.rail.api.models.Run;

import java.io.IOException;
import java.util.List;

/**
 * Created by alpa on 10/27/17
 */
public interface ITestRailRun {

    /**
     * Add new test run for existing test plan.
     * @param masterSuiteId default test suit id (master suite in test rail).
     * @param caseIds all test cases what should be added to run,.
     * */
    Entry createTestRunEntry(int masterSuiteId, List<Integer> caseIds) throws IOException;

    /**
     * Add new test run for existing test plan.
     * @param masterSuiteId default test suit id (master suite in test rail).
     *  !!!WARNING!!! All test from masterSuiteId will be added.
     * */
    Entry createTestRunEntry(int masterSuiteId) throws IOException;

    /**
     * Add new test run.
     * @param masterSuiteId default test suit id (master suite in test rail).
     * @param caseIds all test cases what should be added to run,.
     * */
    Run createTestRun(int masterSuiteId, List<Integer> caseIds);

    /**
     * Add new test run.
     * @param masterSuiteId default test suit id (master suite in test rail).
     *  !!!WARNING!!! All test from masterSuiteId will be added.
     * */
    Run createTestRun(int masterSuiteId);
}
