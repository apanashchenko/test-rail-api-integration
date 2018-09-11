package com.test.rail.api.test.junit4;

import com.test.rail.api.listeners.junit4.rule.TestRailRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by alpa on 10/31/17
 */
//TODO need real test worked host
public class TestJunit4Rule {

//    @Rule
    public TestRailRule testRailRule = new TestRailRule();

//    @Test
    public void testSuccess() {
        assertTrue(true);
    }

//    @Test
    public void testFail() {
        assertTrue(false);

    }

//    @Test
    public void test1() {

    }
}
