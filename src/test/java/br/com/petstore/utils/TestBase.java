package br.com.petstore.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.ValidatableResponse;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TestBase {
    private static ExtentReports extent;
    protected static ExtentTest test;
    protected String endpoint;
    protected Object requestBody;
    protected ValidatableResponse response;

    @BeforeSuite
    public void setupSuite() {
        // Pegando configurações do `global.properties`
        String reportTitle = PropertiesLoader.getProperty("report.title", "PetStore API Test Report");
        String reportName = PropertiesLoader.getProperty("report.name", "Test Results");
        String theme = PropertiesLoader.getProperty("report.theme", "DARK");

        // Gerar um nome de relatório único com data e hora
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String reportFile = "target/extent-report-" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportFile);
        spark.config().setTheme(theme.equalsIgnoreCase("DARK") ? Theme.DARK : Theme.STANDARD);
        spark.config().setDocumentTitle(reportTitle + " - " + timestamp);
        spark.config().setReportName(reportName + " - " + timestamp);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Projeto", "PetStore API");
        extent.setSystemInfo("Autor", "Equipe QA");
        extent.setSystemInfo("Linguagem", "Java 21");
        extent.setSystemInfo("Framework", "TestNG + RestAssured");
    }

    @BeforeMethod
    public void setupTest(Method method) {
        test = extent.createTest("📝 " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestResult(ITestResult result) {
        String baseUrl = PropertiesLoader.getProperty("base.url");

        if (endpoint != null) {
            test.info("<b>🔗 Endpoint:</b> " + baseUrl + endpoint);
        }
        if (requestBody != null) {
            test.info("<b>📤 Request:</b> <pre>" + requestBody.toString() + "</pre>");
        }
        if (response != null) {
            test.info("<b>📥 Response:</b> <pre>" + response.extract().asPrettyString() + "</pre>");
            test.info("<b>📌 Status Code:</b> " + response.extract().statusCode());
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "✅ Teste passou com sucesso!");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "❌ Teste falhou: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "⚠️ Teste pulado.");
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