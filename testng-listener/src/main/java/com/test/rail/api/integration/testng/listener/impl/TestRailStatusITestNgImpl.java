package com.test.rail.api.integration.testng.listener.impl;


import com.google.common.collect.ImmutableMap;
import com.test.rail.api.ITestRailResultStatus;
import org.testng.internal.TestResult;

import java.util.Map;


/**
 * Created by alpa on 10/25/17
 */
public class TestRailStatusITestNgImpl implements ITestRailResultStatus {

    private static final Map<Integer, Integer> TESTNG_STATUS_MAP = ImmutableMap.<Integer, Integer>builder()
            .put(TestResult.SUCCESS, SUCCESS)
            .put(TestResult.FAILURE, FAILURE)
            .put(TestResult.SKIP, REJECTED)
            .build();

    public int getStatus(int testNgStatus) {
        return TESTNG_STATUS_MAP.get(testNgStatus);
    }
}