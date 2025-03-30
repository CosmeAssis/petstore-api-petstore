package br.com.petstore.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    // Configurar GSON para JSON formatado
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @AfterMethod(alwaysRun = true)
    public void logTestResult(ITestResult result) {
        String baseUrl = PropertiesLoader.getProperty("base.url");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("===============================================");
        System.out.println("🔹 Teste: " + result.getMethod().getMethodName());

        if (endpoint != null) {
            test.info("<b>🔗 Endpoint:</b> " + baseUrl + endpoint);
            System.out.println("🔗 Endpoint: " + baseUrl + endpoint);
        }
        if (requestBody != null) {
            String requestJson = gson.toJson(requestBody);
            test.info("<b>📤 Request:</b> <pre>" + requestJson + "</pre>");
            System.out.println("📤 Request:\n" + requestJson);
        }
        if (response != null) {
            String responseJson = gson.toJson(response.extract().asPrettyString());
            test.info("<b>📥 Response:</b> <pre>" + responseJson + "</pre>");
            System.out.println("📥 Response:\n" + responseJson);
            System.out.println("📌 Status Code: " + response.extract().statusCode());
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "✅ Teste passou com sucesso!");
            System.out.println("✅ Teste passou com sucesso!");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "❌ Teste falhou: " + result.getThrowable().getMessage());
            System.out.println("❌ Teste falhou: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "⚠️ Teste pulado.");
            System.out.println("⚠️ Teste pulado.");
        }

        System.out.println("===============================================");
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