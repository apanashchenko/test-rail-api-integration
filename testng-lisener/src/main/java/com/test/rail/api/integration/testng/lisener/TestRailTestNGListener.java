package com.test.rail.api.integration.testng.lisener;

import com.test.rail.api.impl.TestRailApiClientImpl;
import com.test.rail.api.impl.TestRailResultsSenderImpl;
import com.test.rail.api.impl.TestRailRunImpl;
import com.test.rail.api.integration.testng.lisener.impl.TestRailStatusITestNgImpl;
import com.test.rail.api.models.Result;
import com.test.rail.api.parser.DefectIdParser;
import com.test.rail.api.parser.TestCaseIdParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.testng.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.test.rail.api.conf.ConfigLoader.load;


/**
 * Created by alpa on 10/17/17
 */
public class TestRailTestNGListener implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private static final Logger LOG = Logger.getLogger(TestRailTestNGListener.class.getName());
    private static final TestRailResultsSenderImpl TEST_RAIL_RESULT_SENDER =
            TestRailResultsSenderImpl.getInstance(TestRailApiClientImpl.getInstance(), new TestRailRunImpl());

    @Override
    public void onStart(ISuite suite) {
        if (load().postTestRailResult()) {

            TestCaseIdParser testCaseIdParser = new TestCaseIdParser();

            List<ITestNGMethod> testNGMethods = suite.getAllMethods();

            List<Integer> testCaseIdList = new ArrayList<>();

            testNGMethods.forEach(method ->
                    testCaseIdList.addAll(testCaseIdParser.getTestCaseIdValue(
                            method.getConstructorOrMethod().getMethod().getAnnotations())));

            try {
                if (load().useTestRailPlanId() && !load().useTestRailRunId()) {
                    TEST_RAIL_RESULT_SENDER.addRunToPlan(load().testRailPlanId(),
                            load().testRailMasterSuiteId(), testCaseIdList);
                } else if (!load().useTestRailPlanId() && !load().useTestRailRunId()) {
                    TEST_RAIL_RESULT_SENDER.addRun(load().testRailProjectId(),
                            load().testRailMasterSuiteId(), testCaseIdList);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinish(ISuite suite) {

    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        sendResultToTestRail(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()) {
            LOG.info("Method start: " + method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()) {
            LOG.info("Method finish: " + method.getTestMethod().getMethodName());
        }
        sendResultToTestRail(testResult);
    }

    private void sendResultToTestRail(ITestResult iTestResult) {
        if (load().postTestRailResult()) {
            ITestNGMethod method = iTestResult.getMethod();

            int testRailStatus = new TestRailStatusITestNgImpl().getStatus(iTestResult.getStatus());

            TestCaseIdParser testCaseIdParser = new TestCaseIdParser();

            List<Integer> tesCasesIds = testCaseIdParser.getTestCaseIdValue(method.getConstructorOrMethod().getMethod().getDeclaredAnnotations());

            if (method.isTest() && method.getEnabled() && !tesCasesIds.isEmpty()) {
                LOG.info(String.format("Start post result to %s...", load().testRailHost()));

                DefectIdParser defectIdParser = new DefectIdParser();

                List<String> defectIds = defectIdParser.getDefectIdValue(method.getConstructorOrMethod().getMethod().getDeclaredAnnotations());

                String testMethodName = method.getMethodName();
                String version = "1.0";
                String commentsPrefix = "Auto test that has been executed: ";
                String commentClassName = method.getRealClass().getName();
                long runTime = iTestResult.getEndMillis() - iTestResult.getStartMillis();
                Throwable throwable = iTestResult.getThrowable();

                Result testResults = new Result();
                testResults.setStatusId(testRailStatus);
                testResults.setElapsed(createRunTimeFormat(runTime));
                if (throwable != null) {
                    testResults.setComment(String.format("%s %s, failed because of %s", commentsPrefix ,
                            commentClassName + testMethodName, throwable.getMessage()));
                } else {
                    testResults.setComment(commentsPrefix + commentClassName + testMethodName);
                }
                testResults.setVersion(version);
                if (!defectIds.isEmpty()) {
                    testResults.setDefects(StringUtils.join(defectIds, ','));
                }

                tesCasesIds.forEach(testCaseId -> {
                    try {

                        TEST_RAIL_RESULT_SENDER.sendResult(testCaseId, testResults);

                        LOG.info(String.format("Post result for test %s with id %s is success!", testMethodName, testCaseId));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                LOG.info(String.format("Finish post result to %s...", load().testRailHost()));
            }
        }
    }

    private String createRunTimeFormat(long durationMillis) {
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

