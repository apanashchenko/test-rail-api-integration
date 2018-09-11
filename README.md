[![Build Status](https://travis-ci.org/apanashchenko/test-rail-api-integration.svg?branch=master)](https://travis-ci.org/apanashchenko/test-rail-api-integration)


## TestNg + TestRail sample.

This sample shows to you how to change tests statuses of TestRail test run from your automation tests.

To config test-rail-api-integration you should **create property file ‘testRailResult.properties’** and put it into ‘**src/main/resources**’, **attach** to your test class ‘**TestRailTestNGListener.class**’ and annotate tests method with annotation **@TestCaseId(id=...)**

**List of properties of ‘testRailResult.properties’ file:** 

    test.rail.user
    test.rail.password
    test.rail.host
    test.rail.project.id 
    test.rail.master.suite.id 
    test.rail.run.id
    post.test.rail.result
    use.existing.test.run.id

### `test.rail.user, test.rail.password`
 it’s your login and password to TestRail. Be shouer, that your account has permission to update TestRun.

### `test.rail.host`

![](https://lh3.googleusercontent.com/oIb9xkMnXlNNq9GJP_ol5YH1HEOxEPBLZLkvXRgfvedymOvNrHl4UXVwaVL-J9rSHxztZZuBp_Los8NmSPBiJ33a15DulDi59zsASIQVXymC_d4SExQ9Kst7uIalNFVt63qj7YIH)

### `test.rail.project.id`
**![](https://lh3.googleusercontent.com/Q4OyfGyJFElRcbWwJ9lH38zzIoYbwEexVaj_mf_ZJCTTO9f5GBft-EKyHY_4lh-37n08YV6hPLAzriJYYXthHdsP2y5yPd3cxcgEFwRmeXoVyjbpEK-Rd0VYnSixE0GjIC0D-x0K)**
### `test.rail.master.suite.id, @TestCaseId`
**![](https://lh6.googleusercontent.com/NcAC8MHc55itxhrKg1dtArdiXmFnB84AfmTu3xbjrywKzG_rH8-Fki72M7BZaLQk_oysHTyzom1UsOP92QgeRCND4HLOb-1Zod8ZlN0L8Je1LJIMKJTSfQvaQkx7oL2jIe83Y-Ca)**
### `test.rail.run.id`
**![](https://lh6.googleusercontent.com/rcf_7TS8jQSZhNGh2hpq6HwkViH2c9bh8GB9LK6jtynfsApaFbIYNfG2ilfNaXTnVPOWltAhD3ADmMcMNro0Gdk-xm_449FEEuOzl6lRefJMACM15d-q4PQtIPo-VxvK5FnbNmg6)**

So, when you already has all necessary values, you can combine all parts together and enjoin:

### testRailResult.properties file (according to screenshots):

    test.rail.host=your.testrail.domain  
    test.rail.user=your testrail login  
    test.rail.password=your test rail pass  
    test.rail.project.id=37  
    test.rail.master.suite.id=3734  
    test.rail.run.id=6347  
    post.test.rail.result=false  
    use.existing.test.run.id=true

### Test class sample (according to screenshots):

    package smoke.suite;  
    import com.test.rail.api.annotation.TestCaseId;  
    import com.test.rail.api.integration.testng.lisener.TestRailTestNGListener;  
    import org.testng.annotations.Listeners;  
    import org.testng.annotations.Test;  
    
    @Listeners({TestRailTestNGListener.class})  
    public class TestRailSample {  
    
    @TestCaseId(id=6894359)  
    @Test  
    public void sampleTest(){  
    /*your tests actions*/  
    }  
     
    }
