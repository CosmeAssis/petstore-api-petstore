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
import java.util.stream.Collectors;

public abstract class TestBase {
    private static ExtentReports extent;
    protected static ExtentTest test;
    protected String endpoint;
    protected Object requestBody;
    protected ValidatableResponse response;

    @BeforeSuite
    public void setupSuite() {
        String reportTitle = PropertiesLoader.getProperty("report.title", "PetStore API Test Report");
        String reportName = PropertiesLoader.getProperty("report.name", "Test Results");
        String theme = PropertiesLoader.getProperty("report.theme", "DARK");

        // Criando um nome de arquivo único para cada execução
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String reportFile = "target/extent-report-" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportFile);
        spark.config().setTheme(theme.equalsIgnoreCase("DARK") ? Theme.DARK : Theme.STANDARD);
        spark.config().setDocumentTitle(reportTitle + " - " + timestamp);
        spark.config().setReportName(reportName + " - " + timestamp);
        spark.config().setTimelineEnabled(true);

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
        System.out.println("\n🔹 Iniciando teste: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestResult(ITestResult result) {
        String baseUrl = PropertiesLoader.getProperty("base.url");

        if (endpoint != null) {
            String fullUrl = baseUrl + endpoint;
            test.info("<b>🔗 Endpoint:</b> " + fullUrl);
            System.out.println("\n🔗 URL: " + fullUrl);
        }

        if (requestBody != null) {
            test.info("<b>📤 Request:</b> <pre>" + requestBody.toString() + "</pre>");
            System.out.println("\n📤 Request: " + requestBody.toString());
        }

        if (response != null) {
            String responseBody = response.extract().asPrettyString();
            test.info("<b>📥 Response:</b> <pre>" + responseBody + "</pre>");
            test.info("<b>📌 Status Code:</b> " + response.extract().statusCode());

            System.out.println("\n📥 Response:\n" + responseBody);
            System.out.println("\n📌 Status Code: " + response.extract().statusCode());

            // Capturar e exibir os headers
            String headers = response.extract().headers().asList()
                    .stream()
                    .map(header -> header.getName() + ": " + header.getValue())
                    .collect(Collectors.joining("\n"));
            test.info("<b>📡 Headers:</b> <pre>" + headers + "</pre>");
            System.out.println("\n📡 Headers:\n" + headers);
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "✅ Teste passou com sucesso!");
            System.out.println("\n✅ Teste passou com sucesso!");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "❌ Teste falhou: " + result.getThrowable().getMessage());
            System.out.println("\n❌ Teste falhou: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "⚠️ Teste pulado.");
            System.out.println("\n⚠️ Teste pulado.");
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
        System.out.println("\n📊 Relatório gerado: target/extent-report.html");
    }

    protected void setTestDetails(String endpoint, Object requestBody, ValidatableResponse response) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.response = response;
    }
}