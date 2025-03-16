package br.com.petstore.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.ValidatableResponse;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public abstract class TestBase {
    private static ExtentReports extent;
    protected static ExtentTest test;
    protected String endpoint;
    protected Object requestBody;
    protected ValidatableResponse response;

    @BeforeSuite
    public void setupSuite() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setupTest(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void logTestResult(ITestResult result) {
        if (endpoint != null) {
            test.info("**Endpoint:** " + endpoint);
        }
        if (requestBody != null) {
            test.info("**Request:** " + requestBody.toString());
        }
        if (response != null) {
            test.info("**Response:** " + response.extract().asString());
            test.info("**Status Code:** " + response.extract().statusCode());
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Teste passou com sucesso!");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Teste falhou: " + result.getThrowable().getMessage());
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
    }

    protected void setTestDetails(String endpoint, Object requestBody, ValidatableResponse response) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.response = response;
    }
}