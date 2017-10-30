package com.test.rail.api;

import com.test.rail.api.models.Entry;
import com.test.rail.api.models.Result;
import com.test.rail.api.models.Run;
import com.test.rail.api.models.Test;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.List;

/**
 * Created by alpa on 10/27/17
 */
public interface ITestRailApiClient {

    /**
     * Get all test from test run.
     * @param runId run id.
     * */
    List<Test> getTests(int runId) throws IOException;

    /**
     * Send test result.
     * @param testId test case id.
     * @param results entity.
     * */
    Result sendTestResult(int testId, Result results) throws IOException;

    /**
     * Get all test from test run.
     * @param planId run id.
     * @param entry run entity.
     * */
    Entry addRunToPlan(int planId, Entry entry) throws IOException;

    /**
     * Get all test from test run.
     * @param planId run id.
     * @param run run entity.
     * */
    Run addTestRun(int planId, Run run) throws IOException;

    /**
     * Get test run.
     * @param runId run id,
     * */
    Run getRun(int runId) throws IOException;

}
