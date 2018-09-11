package com.test.rail.api.annotation;

import java.lang.annotation.*;

/**
 * Created by alpa on 10/25/17
 */
/**
 * Wrapper annotation for {@link TestCaseId}.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TestCaseIds {

    TestCaseId[] value();
}
