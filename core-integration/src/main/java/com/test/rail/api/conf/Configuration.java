package com.test.rail.api.conf;


import org.aeonbits.owner.Config;

/**
 * Created by alpa on 10/25/17
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources("classpath:testRailResult.properties")
public interface Configuration extends Config {

    /**
     * Test Rail Api settings
     * */
    
    @Key("test.rail.schema")
    @DefaultValue("https")
    String testRailSchema();

    @Key("test.rail.host")
    @DefaultValue("")
    String testRailHost();

    @Key("test.rail.user")
    @DefaultValue("")
    String testRailUser();

    @Key("test.rail.password")
    @DefaultValue("")
    String testRailPassword();

    @Key("test.rail.project.id")
    @DefaultValue("1")
    int testRailProjectId();

    @Key("test.rail.master.suite.id")
    @DefaultValue("1")
    int testRailMasterSuiteId();

    @Key("test.rail.plan.id")
    @DefaultValue("")
    int testRailPlanId();

    @Key("test.rail.run.id")
    @DefaultValue("")
    int testRunPlanId();

    /**
     * Set true/false for posting result to Test Rail
     * */
    @Key("post.test.rail.result")
    @DefaultValue("true")
    boolean postTestRailResult();

    /**
     * Set true/false for use existing test plan in Test Rail
     * */
    @Key("use.test.rail.plan.id")
    @DefaultValue("true")
    boolean useTestRailPlanId();

    /**
     * Set true/false for use existing test run in Test Rail
     * */
    @Key("use.existing.test.run.id")
    @DefaultValue("false")
    boolean useTestRailRunId();

}
