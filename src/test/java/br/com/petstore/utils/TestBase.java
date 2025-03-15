package br.com.petstore.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public abstract class TestBase {
    private static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setupSuite() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setupTest(Method method) {
        String testName = method.getName();
        test = extent.createTest(testName);
    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
    }
}
