package com.test.rail.api.parser;


import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alpa on 10/25/17
 */
public class AnnotationParser {

    /**
     * Extract test case id from annotation
     */
    public <T extends Annotation> List<T> getAnnotations(Annotation[] annotations, Class<T> cla) {
        return Arrays.stream(annotations)
                .filter(Objects::nonNull)
                .filter(a -> a.annotationType().equals(cla))
                .map(cla::cast)
                .collect(Collectors.toList());
    }
}
