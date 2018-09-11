package com.test.rail.api.parser;


import com.test.rail.api.annotation.TestCaseId;
import com.test.rail.api.annotation.TestCaseIds;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alpa on 10/25/17
 */
public class TestCaseIdParser {

    private AnnotationParser annotationParser = new AnnotationParser();

    /**
     * Extract test case id from {@link TestCaseId}
     */
    public List<Integer> getTestCaseIdValue(Annotation[] annotations) {
        List<Integer> ids = new ArrayList<>();

        List<TestCaseId> testCaseIdList = annotationParser.getAnnotations(annotations, TestCaseId.class);
        List<TestCaseIds> testCaseIdsList = annotationParser.getAnnotations(annotations, TestCaseIds.class);

        if (testCaseIdList != null && !testCaseIdList.isEmpty()) {

            ids.addAll(testCaseIdList
                    .stream()
                    .map(TestCaseId::id)
                    .collect(Collectors.toList()));

        } else if (testCaseIdsList != null && !testCaseIdsList.isEmpty()) {
            Collections.singletonList(testCaseIdsList)
                    .forEach(testCaseId -> testCaseId.forEach(tc -> ids.addAll(getValues(tc.value()))));
        }
        return ids;
    }


    private List<Integer> getValues(TestCaseId[] testCaseIds) {
        List<Integer> ids = new ArrayList<>();
        Arrays.asList(testCaseIds)
                .forEach(testCaseId -> ids.add(testCaseId.id()));
        return ids;
    }

}
