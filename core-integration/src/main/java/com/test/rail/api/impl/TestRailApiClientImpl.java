package com.test.rail.api.impl;


import com.test.rail.api.ITestRailApiClient;
import com.test.rail.api.TestRailException;
import com.test.rail.api.models.Entry;
import com.test.rail.api.models.Result;
import com.test.rail.api.models.Run;
import com.test.rail.api.models.Test;
import com.test.rail.api.service.TestRailService;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import static com.test.rail.api.RestClient.createService;
import static com.test.rail.api.conf.ConfigLoader.load;

/**
 * Created by alpa on 10/25/17
 */
public class TestRailApiClientImpl implements ITestRailApiClient {

    private static final int DEFAULT_TIMEOUT = 20000;
    private static volatile TestRailApiClientImpl INSTANCE;
    private static volatile TestRailService TEST_RAIL_SERVICE;

    public static TestRailApiClientImpl getInstance() {
        TestRailApiClientImpl localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (TestRailApiClientImpl.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
//                    try {
//                        TODO fix can't ping test rail host
//                        checkIsHostReachable();

                    INSTANCE = localInstance = new TestRailApiClientImpl();
                    TEST_RAIL_SERVICE = createService(TestRailService.class);
//                    } catch (TestRailException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
        return localInstance;
    }


    public List<Test> getTests(int runId) throws TestRailException {
        try {
            return TEST_RAIL_SERVICE.getTests(runId).execute().body();
        } catch (IOException e) {
            throw new TestRailException(e.getMessage());
        }
    }

    public Result sendTestResult(int testId, Result results) throws TestRailException {
        try {
            return TEST_RAIL_SERVICE.sendTestResult(testId, results).execute().body();
        } catch (IOException e) {
            throw new TestRailException(e.getMessage());
        }
    }

    public Entry addRunToPlan(int planId, Entry entry) throws TestRailException {
        try {
            return TEST_RAIL_SERVICE.addRunToPlan(planId, entry).execute().body();
        } catch (IOException e) {
            throw new TestRailException(e.getMessage());
        }
    }

    public Run addTestRun(int projectId, Run run) throws TestRailException {
        try {
            return TEST_RAIL_SERVICE.addRun(projectId, run).execute().body();
        } catch (IOException e) {
            throw new TestRailException(e.getMessage());
        }
    }

    public Run getRun(int runId) throws TestRailException {
        try {
            return TEST_RAIL_SERVICE.getRun(runId).execute().body();
        } catch (IOException e) {
            throw new TestRailException(e.getMessage());
        }
    }


    private static void checkIsHostReachable() throws TestRailException {
        boolean isReachable = false;
        String host = load().testRailHost();
        try {
            isReachable = InetAddress.getByName(host).isReachable(DEFAULT_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isReachable) {
            throw new TestRailException(String.format("Can't connect to host %s!", host));
        }
    }
}
