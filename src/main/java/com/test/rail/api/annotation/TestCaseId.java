package com.test.rail.api.annotation;

import java.lang.annotation.*;

/**
 * Created by alpa on 10/25/17
 */
/**
 * Used to link tests with test cases in test rail.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Repeatable(TestCaseIds.class)
public @interface TestCaseId {

    int id();
}
