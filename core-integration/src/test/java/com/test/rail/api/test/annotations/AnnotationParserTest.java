package com.test.rail.api.test.annotations;

import com.test.rail.api.annotation.TestCaseId;
import com.test.rail.api.annotation.TestCaseIds;
import com.test.rail.api.parser.AnnotationParser;
import com.test.rail.api.test.classes.*;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by alpa on 10/25/17
 */
public class AnnotationParserTest {

    private AnnotationParser annotationParser = new AnnotationParser();

    @Test
    public void testCaseIdSingleAnnotationsTest() {
        List<TestCaseId> testCaseIds = getList(TestCaseIdSingleAnnotations.class.getDeclaredMethods(), TestCaseId.class);

        assertEquals(testCaseIds.size(), 1, "Wrong TestCaseId annotation type parsing!");
        assertEquals(testCaseIds.get(0).id(), 10, "Wrong TestCaseId annotation id parsing!");
    }

    @Test
    public void testCaseIdRepeatableAnnotationsTest() {
        List<TestCaseIds> testCaseIds = getList(TestCaseIdRepeatableAnnotations.class.getDeclaredMethods(), TestCaseIds.class);

        assertEquals(testCaseIds.size(), 1, "Wrong TestCaseIds annotation type parsing!");
        assertEquals(testCaseIds.get(0).value().length, 2, "Wrong TestCaseIds annotation id parsing!");

        assertEquals(testCaseIds.get(0).value()[0].id(), 2, "Wrong TestCaseId in TestCaseIds id parsing!");
        assertEquals(testCaseIds.get(0).value()[1].id(), 3, "Wrong TestCaseId in TestCaseIds  id parsing!");
    }

    @Test
    public void testCaseIdsSingleAnnotationsTest() {
        List<TestCaseIds> testCaseIds = getList(TestCaseIdsSingleAnnotations.class.getDeclaredMethods(), TestCaseIds.class);

        assertEquals(testCaseIds.size(), 1, "Wrong TestCaseIds annotation type parsing!");
        assertEquals(testCaseIds.get(0).value().length, 1, "Wrong TestCaseIds annotation id parsing!");

        assertEquals(testCaseIds.get(0).value()[0].id(), 4, "Wrong TestCaseId in TestCaseIds id parsing!");
    }

    @Test
    public void testCaseIdsMultipleAnnotationsTest() {
        List<TestCaseIds> testCaseIds = getList(TestCaseIdsMultipleAnnotations.class.getDeclaredMethods(), TestCaseIds.class);

        assertEquals(testCaseIds.size(), 1, "Wrong TestCaseIds annotation type parsing!");
        assertEquals(testCaseIds.get(0).value().length, 3, "Wrong TestCaseIds annotation id parsing!");

        assertEquals(testCaseIds.get(0).value()[0].id(), 5, "Wrong TestCaseId in TestCaseIds id parsing!");
        assertEquals(testCaseIds.get(0).value()[1].id(), 6, "Wrong TestCaseId in TestCaseIds id parsing!");
        assertEquals(testCaseIds.get(0).value()[2].id(), 7, "Wrong TestCaseId in TestCaseIds id parsing!");
    }

    @Test(expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testCaseIdsEmptyAnnotationsTest() {
        List<TestCaseIds> testCaseIds = getList(TestCaseIdsEmptyAnnotations.class.getDeclaredMethods(), TestCaseIds.class);

        assertEquals(testCaseIds.size(), 1, "Wrong TestCaseIds annotation type parsing!");
        assertEquals(testCaseIds.get(0).value().length, 0, "Wrong TestCaseIds annotation empty id parsing!");
        assertEquals(testCaseIds.get(0).value()[0].id(), 5, "Wrong TestCaseId in TestCaseIds id parsing!");
    }

    private <T extends Annotation> List<T> getList(Method[] methods, Class<T> clazz) {
        return annotationParser.getAnnotations(methods[0].getDeclaredAnnotations(), clazz);
    }
}
