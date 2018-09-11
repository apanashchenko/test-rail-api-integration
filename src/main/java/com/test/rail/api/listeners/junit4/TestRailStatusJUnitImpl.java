package com.test.rail.api.listeners.junit4;

import com.google.common.collect.ImmutableMap;
import com.test.rail.api.ITestRailResultStatus;

import java.util.Map;

/**
 * Created by alpa on 10/31/17
 */
public class TestRailStatusJUnitImpl implements ITestRailResultStatus {

    private static final Map<Integer, Integer> JUNIT_STATUS_MAP = ImmutableMap.<Integer, Integer>builder()
            .put(JUnitStatus.SUCCESS, SUCCESS)
            .put(JUnitStatus.FAILURE, FAILURE)
            .put(JUnitStatus.SKIP, REJECTED)
            .build();

    @Override
    public int getStatus(int status) {
        return JUNIT_STATUS_MAP.get(status);
    }
}
