package com.test.rail.api.service;


import com.test.rail.api.models.Entry;
import com.test.rail.api.models.Result;
import com.test.rail.api.models.Run;
import com.test.rail.api.models.Test;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;


/**
 * Created by alpa on 10/25/17
 */
public interface TestRailService {

    @GET("/index.php%3F/api/v2/get_tests/{runId}")
    Call<List<Test>> getTests(@Path("runId") int runId);

    @POST("/index.php%3F/api/v2/add_result/{testId}")
    Call<Result> sendTestResult(@Path("testId") int testId, @Body Result testResults);

    @POST("/index.php%3F/api/v2/add_plan_entry/{planId}")
    Call<Entry> addRunToPlan(@Path("planId") int planId, @Body Entry entry);

    @POST("/index.php%3F/api/v2/add_run/{projectId}")
    Call<Run> addRun(@Path("projectId") int projectId, @Body Run testRun);

    @GET("/index.php%3F/api/v2/get_run/{runId}")
    Call<Run> getRun(@Path("runId") int runId);

}

