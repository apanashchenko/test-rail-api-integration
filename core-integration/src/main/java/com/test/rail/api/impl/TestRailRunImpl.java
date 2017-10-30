package com.test.rail.api.impl;


import com.test.rail.api.ITestRailRun;
import com.test.rail.api.models.Entry;
import com.test.rail.api.models.Run;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alpa on 10/25/17
 */
public class TestRailRunImpl implements ITestRailRun {

    private static String RUN_NAME_PREFIX = "Automation test run";


    public Entry createTestRunEntry(int masterSuiteId, List<Integer> caseIds) throws IOException {
        Entry entry = new Entry();
        entry.setName(RUN_NAME_PREFIX + " " + Calendar.getInstance().getTime().toString());
        entry.setDescription(RUN_NAME_PREFIX);
        entry.setSuiteId(masterSuiteId);
        entry.setIncludeAll(false);
        entry.setCaseIds(caseIds);

        return entry;
    }

    public Entry createTestRunEntry(int masterSuiteId) throws IOException {
        Entry entry = new Entry();
        entry.setName(RUN_NAME_PREFIX + " " + Calendar.getInstance().getTime().toString());
        entry.setDescription(RUN_NAME_PREFIX);
        entry.setSuiteId(masterSuiteId);
        entry.setIncludeAll(true);

        return entry;
    }

    public Run createTestRun(int masterSuiteId, List<Integer> caseIds) {
        Run run = new Run();
        run.setName(RUN_NAME_PREFIX + " " + Calendar.getInstance().getTime().toString());
        run.setDescription(RUN_NAME_PREFIX);
        run.setSuiteId(masterSuiteId);
        run.setCaseIds(caseIds);
        run.setIncludeAll(false);
        return run;
    }

    public Run createTestRun(int masterSuiteId) {
        Run run = new Run();
        run.setName(RUN_NAME_PREFIX + " " + Calendar.getInstance().getTime().toString());
        run.setDescription(RUN_NAME_PREFIX);
        run.setSuiteId(masterSuiteId);
        run.setIncludeAll(true);
        return run;
    }
}
