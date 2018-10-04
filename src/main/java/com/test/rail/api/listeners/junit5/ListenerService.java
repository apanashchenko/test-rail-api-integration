package com.test.rail.api.listeners.junit5;

import com.test.rail.api.impl.ListenerAction;
import com.test.rail.api.impl.TestRailApiClientImpl;
import com.test.rail.api.impl.TestRailResultsSenderImpl;
import com.test.rail.api.impl.TestRailRunImpl;
import com.test.rail.api.parser.DefectIdParser;
import com.test.rail.api.parser.TestCaseIdParser;
import com.test.rail.api.parser.TmsLinkParser;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static com.test.rail.api.ITestRailResultStatus.FAILURE;
import static com.test.rail.api.ITestRailResultStatus.REJECTED;
import static com.test.rail.api.ITestRailResultStatus.SUCCESS;
import static com.test.rail.api.conf.ConfigLoader.load;

public class ListenerService {

    private static final java.util.logging.Logger LOG = Logger.getLogger(ListenerService.class.getName());
    private static final TestRailResultsSenderImpl TEST_RAIL_RESULT_SENDER =
            TestRailResultsSenderImpl.getInstance(TestRailApiClientImpl.getInstance(), new TestRailRunImpl());
    private ListenerAction listenerAction;
    private static ListenerService INSTANCE;

    public static ListenerService getInstance() {
        ListenerService localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (com.test.rail.api.listeners.junit4.ListenerService.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new ListenerService();
                }
            }
        }
        return localInstance;
    }

    public void createTestRun(ExtensionContext context) {
        if (load().postTestRailResult()) {

            TestCaseIdParser testCaseIdParser = new TestCaseIdParser();

            List<Method> methods = Arrays.asList(context.getTestClass().get().getMethods());

            List<Integer> testCaseIdList = new ArrayList<>();

            methods.forEach(method ->
                    testCaseIdList.addAll(testCaseIdParser.getTestCaseIdValue(
                            method.getAnnotations())));

            listenerAction = new ListenerAction(TEST_RAIL_RESULT_SENDER);
            listenerAction.createTestRun(testCaseIdList);
        }
    }

    public void sendResultToTestRail(ExtensionContext context) {
        TestCaseIdParser testCaseIdParser = new TestCaseIdParser();
        List<Integer> tesCasesIds = testCaseIdParser.getTestCaseIdValue(context.getRequiredTestMethod().getDeclaredAnnotations());
        processTestMethodExecutionResult(context, tesCasesIds);
    }

    public void sendAllureResultToTestRail(ExtensionContext context) {
        TmsLinkParser tmsLinkParser = new TmsLinkParser();
        List<Integer> tesCasesIds = tmsLinkParser.getTmsLinkValue(context.getRequiredTestMethod().getDeclaredAnnotations());
        processTestMethodExecutionResult(context, tesCasesIds);
    }

    private void processTestMethodExecutionResult(ExtensionContext context, List<Integer> ids){
        if (!load().postTestRailResult()) return;
        LOG.info(String.format("Start post result to %s...", load().testRailHost()));
        int testRailStatus = getStatus(context);
        Method method = context.getRequiredTestMethod();

        DefectIdParser defectIdParser = new DefectIdParser();
        List<String> defectIds = defectIdParser.getDefectIdValue(method.getDeclaredAnnotations());

        String testMethodName = method.getName();
        String version = "1.0";
        String commentsPrefix = "Auto test that has been executed: ";
        String commentClassName =  method.getDeclaringClass().getName();

        com.test.rail.api.models.Result testResults = new com.test.rail.api.models.Result();
        testResults.setStatusId(testRailStatus);

        if (context.getExecutionException().isPresent()) {
            testResults.setComment(String.format("%s %s, failed because of %s", commentsPrefix,
                    commentClassName + "." + testMethodName, context.getExecutionException().get().getMessage()));
        } else {
            testResults.setComment(commentsPrefix + commentClassName + "." + testMethodName);
        }
        testResults.setVersion(version);
        if (!defectIds.isEmpty()) {
            testResults.setDefects(StringUtils.join(defectIds, ','));
        }

        listenerAction.sendResult(ids, testResults, testMethodName);
        LOG.info(String.format("Finish post result to %s...", load().testRailHost()));
    }

    private int getStatus(ExtensionContext context) {
        Disabled disabled = context.getTestMethod().get().getDeclaredAnnotation(Disabled.class);
        if (disabled != null) return REJECTED;
        return context.getExecutionException().isPresent() ? FAILURE : SUCCESS;
    }

}
