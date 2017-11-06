package com.test.rail.api.test.annotations;

import com.test.rail.api.parser.TestCaseIdParser;
import com.test.rail.api.test.example.*;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by alpa on 10/25/17
 */
public class TestCaseIdAnnotationParserTest {

    private TestCaseIdParser testCaseIdParser;

    @BeforeMethod
    public void setUp() throws Exception {
        testCaseIdParser = Whitebox.invokeConstructor(TestCaseIdParser.class);
    }

    @Test
    public void testCaseIdSingleAnnotationsTest() {
        List<Integer> testCaseIdValues = getList(TestCaseIdSingleAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 1, "Wrong TestCaseId annotation type parsing!");
        assertEquals(testCaseIdValues.get(0).intValue(), 10, "Wrong TestCaseId annotation id parsing!");
    }

    @Test
    public void testCaseIdRepeatableAnnotationsTest() {
        List<Integer> testCaseIdValues = getList(TestCaseIdRepeatableAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 2, "Wrong TestCaseIds annotation id parsing!");

        assertEquals(testCaseIdValues.get(0).intValue(), 2, "Wrong TestCaseId in TestCaseIds id parsing!");
        assertEquals(testCaseIdValues.get(1).intValue(), 3, "Wrong TestCaseId in TestCaseIds  id parsing!");
    }

    @Test
    public void testCaseIdsSingleAnnotationsTest() {
        List<Integer> testCaseIdValues = getList(TestCaseIdsSingleAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 1, "Wrong TestCaseIds annotation type parsing!");

        assertEquals(testCaseIdValues.get(0).intValue(), 4, "Wrong TestCaseId in TestCaseIds id parsing!");
    }

    @Test
    public void testCaseIdsMultipleAnnotationsTest() {
        List<Integer> testCaseIdValues = getList(TestCaseIdsMultipleAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 3, "Wrong TestCaseIds annotation id parsing!");

        assertEquals(testCaseIdValues.get(0).intValue(), 5, "Wrong TestCaseId in TestCaseIds id parsing!");
        assertEquals(testCaseIdValues.get(1).intValue(), 6, "Wrong TestCaseId in TestCaseIds id parsing!");
        assertEquals(testCaseIdValues.get(2).intValue(), 7, "Wrong TestCaseId in TestCaseIds id parsing!");
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testCaseIdsEmptyAnnotationsTest() {
        List<Integer> testCaseIdValues = getList(TestCaseIdsEmptyAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 0, "Wrong TestCaseIds annotation type parsing!");
        assertEquals(testCaseIdValues.get(0).intValue(), 0, "Wrong TestCaseId in TestCaseIds id parsing!");
    }

    private List<Integer> getList(Method[] methods) {
        return testCaseIdParser.getTestCaseIdValue(methods[0].getDeclaredAnnotations());
    }
}
