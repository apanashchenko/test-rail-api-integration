package com.test.rail.api.impl;

import com.test.rail.api.ITestRailResultsSender;
import com.test.rail.api.models.Result;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.test.rail.api.conf.ConfigLoader.load;

/**
 * Created by alpa on 10/31/17
 */
public class ListenerAction {

    private static final Logger LOG = Logger.getLogger(ListenerAction.class.getName());
    private ITestRailResultsSender railResultsSender;

    public ListenerAction(ITestRailResultsSender railResultsSender) {
        this.railResultsSender = railResultsSender;
    }

    public void createTestRun(List<Integer> testCaseIdList) {
        try {
            if (load().useTestRailPlanId() && !load().useTestRailRunId()) {
                railResultsSender.addRunToPlan(load().testRailPlanId(),
                        load().testRailMasterSuiteId(), testCaseIdList);
            } else if (!load().useTestRailPlanId() && !load().useTestRailRunId()) {
                railResultsSender.addRun(load().testRailProjectId(),
                        load().testRailMasterSuiteId(), testCaseIdList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResult(List<Integer> tesCasesIds, Result testResults, String testMethodName) {
        tesCasesIds.forEach(testCaseId -> {
            try {

                railResultsSender.sendResult(testCaseId, testResults);

                LOG.info(String.format("Post result for test %s with id %s is success!", testMethodName, testCaseId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public String createRunTimeFormat(long durationMillis) {
        String MINIMAL_RUN_TIME = "1s";
        String ZERO_RUN_TIME = "0";
        String ELAPSED_TIME_FORMAT = "H:m:s";
        String COLON_SPLIT = ":";
        String format = DurationFormatUtils.formatDuration(durationMillis, ELAPSED_TIME_FORMAT, true);
        String[] formatSplit = format.split(COLON_SPLIT);
        if (!formatSplit[0].equalsIgnoreCase(ZERO_RUN_TIME)) {
            return String.format("%sh %sm %ss", formatSplit[0], formatSplit[1], formatSplit[2]);
        } else if (!formatSplit[1].equalsIgnoreCase(ZERO_RUN_TIME)) {
            return String.format("%sm %ss", formatSplit[1], formatSplit[2]);
        } else if (!formatSplit[2].equalsIgnoreCase(ZERO_RUN_TIME)) {
            return String.format("%ss", formatSplit[2]);
        } else {
            return MINIMAL_RUN_TIME;
        }
    }
}
