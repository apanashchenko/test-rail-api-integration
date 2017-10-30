package com.test.rail.api.annotation;

import java.lang.annotation.*;

/**
 * Created by alpa on 10/26/17
 */
/**
 * Used to link bugs in test rail.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DefectIds {

    DefectId[] value();
}
