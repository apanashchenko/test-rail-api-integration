package com.junit.rule;

import com.test.rail.integration.junit.rule.TestRailRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by alpa on 10/31/17
 */
public class TestJunit4Rule {

    @Rule
    public TestRailRule testRailRule = new TestRailRule();

    @Test
    public void testSuccess() {
        assertTrue(true);
    }

    @Test
    public void testFail() {
        assertTrue(false);

    }

    @Test
    public void test1() {

    }
}
