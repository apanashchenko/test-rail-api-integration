package com.test.rail.api.test.annotations;

import com.test.rail.api.parser.DefectIdParser;
import com.test.rail.api.test.example.*;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by alpa on 10/26/17
 */
public class DefectIdAnnotationParserTest {

    private DefectIdParser defectIdParser;

    @BeforeMethod
    public void setUp() throws Exception {
        defectIdParser = Whitebox.invokeConstructor(DefectIdParser.class);
    }

    @Test
    public void defectIdSingleAnnotationsTest() {
        List<String> testCaseIdValues = getList(TestCaseIdSingleAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 1, "Wrong DefectId annotation type parsing!");
        assertEquals(testCaseIdValues.get(0), "BUG-1", "Wrong DefectId annotation id parsing!");
    }

    @Test
    public void defectIdRepeatableAnnotationsTest() {
        List<String> testCaseIdValues = getList(TestCaseIdRepeatableAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 2, "Wrong DefectId annotation id parsing!");

        assertEquals(testCaseIdValues.get(0), "BUG-2", "Wrong DefectId in DefectIds id parsing!");
        assertEquals(testCaseIdValues.get(1), "BUG-3", "Wrong DefectId in DefectIds  id parsing!");
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void defectsIdsEmptyAnnotationsTest() {
        List<String> testCaseIdValues = getList(TestCaseIdsEmptyAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 0, "Wrong DefectIds annotation type parsing!");
        assertEquals(testCaseIdValues.get(0), "", "Wrong DefectId in DefectIds id parsing!");
    }

    @Test
    public void defectIdsMultipleAnnotationsTest() {
        List<String> testCaseIdValues = getList(TestCaseIdsMultipleAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 3, "Wrong DefectIds annotation id parsing!");

        assertEquals(testCaseIdValues.get(0), "BUG-4", "Wrong DefectId in DefectIds id parsing!");
        assertEquals(testCaseIdValues.get(1), "BUG-5", "Wrong DefectId in DefectIds id parsing!");
        assertEquals(testCaseIdValues.get(2), "BUG-6", "Wrong DefectId in DefectIds id parsing!");
    }

    @Test
    public void defectIdsSingleAnnotationsTest() {
        List<String> testCaseIdValues = getList(TestCaseIdsSingleAnnotations.class.getDeclaredMethods());

        assertEquals(testCaseIdValues.size(), 1, "Wrong DefectIds annotation type parsing!");

        assertEquals(testCaseIdValues.get(0), "BUG-8", "Wrong DefectId in DefectIds id parsing!");
    }


    private List<String> getList(Method[] methods) {
        return defectIdParser.getDefectIdValue(methods[0].getDeclaredAnnotations());
    }
}
