package com.test.rail.api.impl;


import com.test.rail.api.ITestRailApiClient;
import com.test.rail.api.ITestRailResultsSender;
import com.test.rail.api.ITestRailRun;
import com.test.rail.api.TestRailException;
import com.test.rail.api.models.Entry;
import com.test.rail.api.models.Result;
import com.test.rail.api.models.Run;
import com.test.rail.api.models.Test;

import java.io.IOException;
import java.util.List;

import static com.test.rail.api.conf.ConfigLoader.load;

/**
 * Created by alpa on 10/25/17
 */
public class TestRailResultsSenderImpl implements ITestRailResultsSender {

    private static volatile TestRailResultsSenderImpl INSTANCE;
    private ITestRailApiClient testRailApiClient;
    private ITestRailRun testRailRun;
    private List<Test> testsList;
    private Run testRun;


    public static TestRailResultsSenderImpl getInstance(ITestRailApiClient testRailApiClient, ITestRailRun testRailRun) {
        TestRailResultsSenderImpl localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (TestRailResultsSenderImpl.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new TestRailResultsSenderImpl(testRailApiClient, testRailRun);
                }
            }
        }
        return localInstance;
    }

    private TestRailResultsSenderImpl(ITestRailApiClient testRailApiClient, ITestRailRun testRailRun) {
        this.testRailApiClient = testRailApiClient;
        this.testRailRun = testRailRun;
    }


    public Result sendResult(int testCaseId, Result result) throws TestRailException {
        Test currentTest = getTest(testCaseId, getTestList());

        try {
            return testRailApiClient.sendTestResult(currentTest.getId(), result);
        } catch (IOException ex) {
            throw new TestRailException(
                    String.format("Could not send result to TestRail for test id: %s and test case id: %s!",
                            testCaseId, testCaseId));
        }
    }


    public Run addRunToPlan(int testRailPlanId, int testRailMasterSuiteId, List<Integer> testCaseIdList) throws TestRailException {
        if (testRun == null) {
            try {

                Entry entry = testRailRun.createTestRunEntry(testRailMasterSuiteId, testCaseIdList);
                return testRun = testRailApiClient.addRunToPlan(testRailPlanId, entry).getRuns().get(0);
            } catch (IOException e) {
                throw new TestRailException(e.getMessage());
            }
        }

        return testRun;
    }

    public Run addRunToPlan(int testRailPlanId, int testRailMasterSuiteId) throws TestRailException {
        if (testRun == null) {
            try {
                Entry run = testRailRun.createTestRunEntry(testRailMasterSuiteId);
                return testRun = testRailApiClient.addRunToPlan(testRailPlanId, run).getRuns().get(0);
            } catch (IOException e) {
                throw new TestRailException(e.getMessage());
            }
        }

        return testRun;
    }


    public Run addRun(int projectId, int testRailMasterSuiteId, List<Integer> testCaseIdList) throws TestRailException {
        if (testRun == null) {
            try {

                Run run = testRailRun.createTestRun(testRailMasterSuiteId, testCaseIdList);
                return testRun = testRailApiClient.addTestRun(projectId, run);
            } catch (IOException e) {
                throw new TestRailException(e.getMessage());
            }
        }

        return testRun;
    }


    public Run addRun(int projectId, int testRailMasterSuiteId) throws TestRailException {
        if (testRun == null) {
            try {
                Run run = testRailRun.createTestRun(testRailMasterSuiteId);
                return testRun = testRailApiClient.addTestRun(projectId, run);
            } catch (IOException e) {
                throw new TestRailException(e.getMessage());
            }
        }

        return testRun;
    }


    private List<Test> getTestList() throws TestRailException {
        if (testsList == null) {
            try {
                return testsList = testRailApiClient.getTests(getRunId());
            } catch (IOException e) {
                throw new TestRailException(e.getMessage());
            }
        }

        return testsList;
    }

    private Test getTest(int caseId, List<Test> testsList) throws TestRailException {
        return testsList.stream().filter(test -> test.getCaseId().equals(caseId))
                .findFirst()
                .orElseThrow(() -> new TestRailException("Test case with id " + caseId + " not found!"));
    }

    private int getRunId() throws TestRailException {
        String value;
        try {
            value = String.valueOf(load().testRunPlanId());
        } catch (UnsupportedOperationException e) {
            throw new TestRailException(e.getMessage());
        }

        int runId = Integer.parseInt(value);
        if (!load().useTestRailRunId()) {
            runId = testRun.getId();
        }
        if (runId == 0) {
            throw new TestRailException("Test run id is not set up!");
        }
        return runId;
    }

}