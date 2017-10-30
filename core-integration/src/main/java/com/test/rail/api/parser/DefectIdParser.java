package com.test.rail.api.parser;


import com.test.rail.api.annotation.DefectId;
import com.test.rail.api.annotation.DefectIds;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alpa on 10/25/17
 */
public class DefectIdParser {

    private AnnotationParser annotationParser = new AnnotationParser();

    /**
     * Extract test case id from {@link DefectId}
     */
    public List<String> getDefectIdValue(Annotation[] annotations) {
        List<String> ids = new ArrayList<>();

        List<DefectId> defectIdList = annotationParser.getAnnotations(annotations, DefectId.class);
        List<DefectIds> defectIdsList = annotationParser.getAnnotations(annotations, DefectIds.class);

        if (defectIdList != null && !defectIdList.isEmpty()) {

            ids.addAll(defectIdList
                    .stream()
                    .map(testCaseId -> testCaseId.value().trim())
                    .collect(Collectors.toList()));

        } else if (defectIdsList != null && !defectIdsList.isEmpty()) {
            Collections.singletonList(defectIdsList)
                    .forEach(testCaseId -> testCaseId.forEach(tc -> ids.addAll(getValues(tc.value()))));
        }
        return ids;
    }


    private List<String> getValues(DefectId[] defectIds) {
        List<String> ids = new ArrayList<>();
        Arrays.asList(defectIds)
                .forEach(testCaseId -> ids.add(testCaseId.value().trim()));
        return ids;
    }

}
