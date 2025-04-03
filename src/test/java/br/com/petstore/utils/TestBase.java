package br.com.petstore.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Classe base para testes automatizados com TestNG e RestAssured.
 * Configura relatórios, captura logs e gerencia informações de requisição e resposta.
 */
public abstract class TestBase {
    private static ExtentReports extent; // Objeto para gerar relatórios
    protected static ExtentTest test; // Objeto para capturar logs dos testes
    protected String endpoint; // Armazena o endpoint da requisição atual
    protected Object requestBody; // Armazena o corpo da requisição
    protected ValidatableResponse response; // Armazena a resposta da API

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, Integer> testSummary = new HashMap<>(); // Contador de testes executados

    /**
     * Configuração inicial dos relatórios antes da suíte de testes iniciar.
     */
    @BeforeSuite
    public void setupSuite() {
        // Obtém configurações do relatório a partir de propriedades externas
        String reportTitle = PropertiesLoader.getProperty("report.title", "PetStore API Test Report");
        String reportName = PropertiesLoader.getProperty("report.name", "Test Results");
        String theme = PropertiesLoader.getProperty("report.theme", "DARK");

        // Criação do arquivo de relatório com timestamp para evitar sobrescrita
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String reportFile = "target/extent-report-" + timestamp + ".html";

        // Configuração do ExtentSparkReporter (interface do relatório)
        ExtentSparkReporter spark = new ExtentSparkReporter(reportFile);
        spark.config().setTheme(theme.equalsIgnoreCase("DARK") ? Theme.DARK : Theme.STANDARD);
        spark.config().setDocumentTitle(reportTitle + " - " + timestamp);
        spark.config().setReportName(reportName + " - " + timestamp);
        spark.config().setTimelineEnabled(true); // Ativa linha do tempo para melhor visualização
        spark.config().setEncoding("UTF-8");

        // Adiciona estilos customizados para diferenciar status dos testes
        String customCSS = ".passed { background-color: #28a745; color: white; padding: 5px; } " +
                ".failed { background-color: #dc3545; color: white; padding: 5px; } " +
                ".skipped { background-color: #ffc107; color: black; padding: 5px; }";
        spark.config().setCss(customCSS);

        // Inicializa o relatório
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Projeto", "PetStore API");
        extent.setSystemInfo("Autor", "Equipe QA");
        extent.setSystemInfo("Linguagem", "Java 21");
        extent.setSystemInfo("Framework", "TestNG + RestAssured");

        // Inicializa contagem de testes
        testSummary.put("Passed", 0);
        testSummary.put("Failed", 0);
        testSummary.put("Skipped", 0);
    }

    /**
     * Configura cada teste antes de sua execução.
     * Exibe a descrição da anotação @Test se disponível.
     */
    @BeforeMethod
    public void setupTest(Method method) {
        // Obtém a anotação @Test do método
        Test testAnnotation = method.getAnnotation(Test.class);
        String testName = (testAnnotation != null && !testAnnotation.description().isEmpty())
                ? testAnnotation.description() // Usa a descrição se existir
                : method.getName(); // Caso contrário, usa o nome do método

        // Cria um novo registro no relatório para o teste atual
        test = extent.createTest("📝 " + testName);
        test.assignCategory(method.getDeclaringClass().getSimpleName());
        System.out.println("\n🔹 Iniciando teste: " + testName);
    }

    /**
     * Captura e registra os resultados de cada teste após a execução.
     */
    @AfterMethod(alwaysRun = true)
    public void logTestResult(ITestResult result) {
        String baseUrl = PropertiesLoader.getProperty("base.url");
        long duration = (result.getEndMillis() - result.getStartMillis()) / 1000;

        System.out.println("\n===============================================");
        System.out.println("🔹 Teste: " + result.getMethod().getMethodName());

        // Registra o endpoint da requisição
        if (endpoint != null) {
            test.info("<b>🔗 Endpoint:</b> " + baseUrl + endpoint);
            System.out.println("🔗 Endpoint: " + baseUrl + endpoint);
        }

        // Registra o corpo da requisição
        if (requestBody != null) {
            String requestJson = gson.toJson(requestBody);
            test.info("<b>📤 Request:</b> <pre>" + requestJson + "</pre>");
            System.out.println("📤 Request:\n" + requestJson);
        }

        // Registra o corpo da resposta
        if (response != null) {
            String responseJson = response.extract().asPrettyString();
            test.info("<b>📥 Response:</b> <pre>" + responseJson + "</pre>");
            System.out.println("📥 Response:\n" + responseJson);
            System.out.println("📌 Status Code: " + response.extract().statusCode());
        }

        // Registra os headers da resposta (se disponíveis)
        if (response != null) {
            String headers = response.extract().headers().toString();
            test.info("<b>📌 Headers:</b> <pre>" + headers + "</pre>");
            System.out.println("📌 Headers:\n" + headers);

            // Registra parâmetros da requisição (se disponíveis)
            String params = response.extract().cookies().toString();
            if (!params.isEmpty()) {
                test.info("<b>🛠 Parâmetros:</b> <pre>" + params + "</pre>");
                System.out.println("🛠 Parâmetros:\n" + params);
            }
        }

        // Registra o resultado do teste no relatório
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                test.log(Status.PASS, "✅ Teste passou com sucesso!");
                test.info("<b>Tempo de execução:</b> " + duration + "s");
                break;
            case ITestResult.FAILURE:
                test.log(Status.FAIL, "❌ Teste falhou: " + result.getThrowable().getMessage());
                test.info("<b>Tempo de execução:</b> " + duration + "s");
                break;
            case ITestResult.SKIP:
                test.log(Status.SKIP, "⚠️ Teste pulado.");
                test.info("<b>Tempo de execução:</b> " + duration + "s");
                break;
        }

        System.out.println("===============================================\n");
    }

    /**
     * Finaliza o relatório e exibe um sumário dos testes.
     */
    @AfterSuite
    public void tearDownSuite() {
        // Configura análise de testes por categoria
        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);

        // Gera o relatório final
        extent.flush();
        System.out.println("\n📊 Relatório gerado: target/extent-report.html");
    }

    /**
     * Configura detalhes da requisição antes da execução do teste.
     *
     * @param endpoint     Endpoint da API
     * @param requestBody  Corpo da requisição
     * @param response     Resposta da API
     */
    protected void setTestDetails(String endpoint, Object requestBody, ValidatableResponse response) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.response = response;
    }
}