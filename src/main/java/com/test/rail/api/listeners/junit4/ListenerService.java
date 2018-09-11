package com.test.rail.api.listeners.junit4;

import com.test.rail.api.impl.ListenerAction;
import com.test.rail.api.impl.TestRailApiClientImpl;
import com.test.rail.api.impl.TestRailResultsSenderImpl;
import com.test.rail.api.impl.TestRailRunImpl;
import com.test.rail.api.parser.DefectIdParser;
import com.test.rail.api.parser.TestCaseIdParser;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.runner.Description;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static com.test.rail.api.conf.ConfigLoader.load;

/**
 * Created by alpa on 10/31/17
 */
public class ListenerService {

    private static final Logger LOG = Logger.getLogger(ListenerService.class.getName());
    private static final TestRailResultsSenderImpl TEST_RAIL_RESULT_SENDER =
            TestRailResultsSenderImpl.getInstance(TestRailApiClientImpl.getInstance(), new TestRailRunImpl());
    private ListenerAction listenerAction;
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


    public void createTestRun(Description description) {
        if (load().postTestRailResult()) {

            TestCaseIdParser testCaseIdParser = new TestCaseIdParser();

            List<Method> methods = Arrays.asList(description.getTestClass().getMethods());

            List<Integer> testCaseIdList = new ArrayList<>();

            methods.forEach(method ->
                    testCaseIdList.addAll(testCaseIdParser.getTestCaseIdValue(
                            method.getAnnotations())));

            listenerAction = new ListenerAction(TEST_RAIL_RESULT_SENDER);
            listenerAction.createTestRun(testCaseIdList);
        }
    }

    public void sendResultToTestRail(Description description, Throwable throwable) {
        if (load().postTestRailResult()) {

            String methodName = description.getMethodName();

            Method method = null;
            try {
                method = description.getTestClass().getMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return;
            }

            if (method != null) {

                TestRailStatusJUnitImpl testRailStatusJUnit = new TestRailStatusJUnitImpl();
                int testRailStatus = 0;

                Ignore ignore = method.getDeclaredAnnotation(Ignore.class);

                if (ignore == null && throwable == null) {
                    testRailStatus = testRailStatusJUnit.getStatus(JUnitStatus.SUCCESS);
                } else if (throwable != null) {
                    testRailStatus = testRailStatusJUnit.getStatus(JUnitStatus.FAILURE);
                } else if (ignore != null) {
                    testRailStatus = testRailStatusJUnit.getStatus(JUnitStatus.SKIP);
                }

                TestCaseIdParser testCaseIdParser = new TestCaseIdParser();

                List<Integer> tesCasesIds = testCaseIdParser.getTestCaseIdValue(method.getAnnotations());

                if (description.isTest() && !tesCasesIds.isEmpty() && ignore == null) {
                    LOG.info(String.format("Start post result to %s...", load().testRailHost()));

                    DefectIdParser defectIdParser = new DefectIdParser();

                    List<String> defectIds = defectIdParser.getDefectIdValue(method.getDeclaredAnnotations());

                    String testMethodName = method.getName();
                    String version = "1.0";
                    String commentsPrefix = "Auto test that has been executed: ";
                    String commentClassName = method.getClass().getName();
//                long runTime = iTestResult.getEndMillis() - iTestResult.getStartMillis();

                    com.test.rail.api.models.Result testResults = new com.test.rail.api.models.Result();
                    testResults.setStatusId(testRailStatus);

//                testResults.setElapsed(createRunTimeFormat(runTime));
//                Throwable throwable = failure.getException();

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

                    listenerAction.sendResult(tesCasesIds, testResults, testMethodName);
                    LOG.info(String.format("Finish post result to %s...", load().testRailHost()));
                }
            }
        }
    }
}
