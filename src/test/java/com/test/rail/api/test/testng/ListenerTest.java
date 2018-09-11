package com.test.rail.api.test.testng;

import com.test.rail.api.listeners.testng.TestRailTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by alpa on 10/31/17
 */
//TODO need real test worked host
//@Listeners(TestRailTestNGListener.class)
public class ListenerTest {

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
