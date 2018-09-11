package com.test.rail.api;

import com.test.rail.api.models.Result;
import com.test.rail.api.models.Run;

import java.util.List;

/**
 * Created by alpa on 10/26/17
 */
public interface ITestRailResultsSender {

    /**
     * Post custom test result to Test Rail.
     * @param testCaseId current test case id.
     * @param testResults test result.
     */
    Result sendResult(int testCaseId, Result testResults) throws TestRailException;

    /**
     * Create new test run inside test plan to Test Rai.
     * @param testRailPlanId existing test plan id.
     * @param testRailMasterSuiteId main suite with all test cases.
     * @param testCaseIdList test cases ids
     */
    Run addRunToPlan(int testRailPlanId, int testRailMasterSuiteId, List<Integer> testCaseIdList) throws TestRailException;

    /**
     * Create new test run inside test plan to Test Rai.
     * @param testRailPlanId existing test plan id.
     * @param testRailMasterSuiteId main suite with all test cases.
     */
    Run addRunToPlan(int testRailPlanId, int testRailMasterSuiteId) throws TestRailException;

    /**
     * Create new test run to Test Rail.
     * @param projectId existing Test Rail project id.
     * @param testRailMasterSuiteId main suite with all test cases.
     * @param testCaseIdList test cases ids .
     */
    Run addRun(int projectId, int testRailMasterSuiteId, List<Integer> testCaseIdList) throws TestRailException;

    /**
     * Create new test run to Test Rail.
     * @param projectId existing Test Rail project id.
     * @param testRailMasterSuiteId main suite with all test cases.
     */
    Run addRun(int projectId, int testRailMasterSuiteId) throws TestRailException;


}
