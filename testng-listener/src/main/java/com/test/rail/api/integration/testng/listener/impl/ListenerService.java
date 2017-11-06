package com.test.rail.api.integration.testng.listener.impl;

import com.test.rail.api.ITestRailApiClient;
import com.test.rail.api.ITestRailRun;
import com.test.rail.api.impl.ListenerAction;
import com.test.rail.api.impl.TestRailApiClientImpl;
import com.test.rail.api.impl.TestRailResultsSenderImpl;
import com.test.rail.api.impl.TestRailRunImpl;
import com.test.rail.api.integration.testng.listener.TestRailTestNGListener;
import com.test.rail.api.models.Result;
import com.test.rail.api.parser.DefectIdParser;
import com.test.rail.api.parser.TestCaseIdParser;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.test.rail.api.conf.ConfigLoader.load;

/**
 * Created by alpa on 10/31/17
 */
public class ListenerService {

    private static final Logger LOG = Logger.getLogger(TestRailTestNGListener.class.getName());
    private static final TestRailResultsSenderImpl TEST_RAIL_RESULT_SENDER =
            TestRailResultsSenderImpl.getInstance(TestRailApiClientImpl.getInstance(), new TestRailRunImpl());
    private ListenerAction listenerAction = new ListenerAction(TEST_RAIL_RESULT_SENDER);
    private static ListenerService INSTANCE;


    public static ListenerService getInstance() {
        ListenerService localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (ListenerService.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new ListenerService();
                }
            }
        }
        return localInstance;
    }


    public void createTestRun(List<Integer> testCaseIdList) {
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

    public void sendResultToTestRail(ITestResult iTestResult) {
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
                testResults.setElapsed(listenerAction.createRunTimeFormat(runTime));
                if (throwable != null) {
                    testResults.setComment(String.format("%s %s, failed because of %s", commentsPrefix,
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
}
