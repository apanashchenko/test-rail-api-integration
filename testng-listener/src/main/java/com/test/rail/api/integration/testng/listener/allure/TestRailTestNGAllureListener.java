package com.test.rail.api.integration.testng.listener.allure;

import com.test.rail.api.integration.testng.listener.impl.ListenerService;
import com.test.rail.api.integration.testng.listener.impl.TmsLinkParser;
import org.testng.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.test.rail.api.conf.ConfigLoader.load;


/**
 * Created by alpa on 10/17/17
 */
public class TestRailTestNGAllureListener implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private static final Logger LOG = Logger.getLogger(TestRailTestNGAllureListener.class.getName());

    @Override
    public void onStart(ISuite suite) {
        if (load().postTestRailResult()) {

            TmsLinkParser tmsLinkParser = new TmsLinkParser();

            List<ITestNGMethod> testNGMethods = suite.getAllMethods();

            List<Integer> testCaseIdList = new ArrayList<>();

            testNGMethods.forEach(method ->
                    testCaseIdList.addAll(tmsLinkParser.getTmsLinkValue(
                            method.getConstructorOrMethod().getMethod().getAnnotations())));

            ListenerService.getInstance().createTestRun(testCaseIdList);
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
        ListenerService.getInstance().sendResultToTestRail(result);
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
        ListenerService.getInstance().sendResultToTestRail(testResult);
    }
}

