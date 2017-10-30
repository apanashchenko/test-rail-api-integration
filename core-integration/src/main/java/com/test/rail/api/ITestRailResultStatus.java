package com.test.rail.api;


/**
 * Created by alpa on 10/26/17
 */
public interface ITestRailResultStatus {

    /**
     * Find corresponding test status in xUnit system.
     * @param status xUnit test status.
     * */
    int getStatus(int status);

    int SUCCESS = 1;
    int FAILURE = 5;
    int REJECTED = 4;

}
