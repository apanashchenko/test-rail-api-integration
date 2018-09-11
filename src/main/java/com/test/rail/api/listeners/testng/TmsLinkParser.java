package com.test.rail.api.listeners.testng;


import com.test.rail.api.parser.AnnotationParser;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alpa on 10/30/17
 */
public class TmsLinkParser {

    private AnnotationParser annotationParser = new AnnotationParser();

    /**
     * Extract test case id from {@link TmsLink}
     */
    public List<Integer> getTmsLinkValue(Annotation[] annotations) {
        List<Integer> ids = new ArrayList<>();

        List<TmsLink> tmsLinkList = annotationParser.getAnnotations(annotations, TmsLink.class);
        List<TmsLinks> tmsLinksList = annotationParser.getAnnotations(annotations, TmsLinks.class);

        if (tmsLinkList != null && !tmsLinkList.isEmpty()) {

            ids.addAll(tmsLinkList
                    .stream()
                    .map(tmsLink -> Integer.valueOf(tmsLink.value()))
                    .collect(Collectors.toList()));

        } else if (tmsLinksList != null && !tmsLinksList.isEmpty()) {
            Collections.singletonList(tmsLinksList)
                    .forEach(testCaseId -> testCaseId.forEach(tc -> ids.addAll(getValues(tc.value()))));
        }
        return ids;
    }


    private List<Integer> getValues(TmsLink[] testCaseIds) {
        List<Integer> ids = new ArrayList<>();
        Arrays.asList(testCaseIds)
                .forEach(testCaseId -> ids.add(Integer.valueOf(testCaseId.value())));
        return ids;
    }

}
