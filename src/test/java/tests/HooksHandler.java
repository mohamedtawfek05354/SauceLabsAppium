package tests;

import Reuse.BaseTest;
import Device.Device;
import Reuse.Screenshots;
import Reuse.SeleniumAIAnalyzer;
import appiumServer.appiumServer;
import driver.MobileDriver;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static java.lang.invoke.MethodHandles.lookup;

public class HooksHandler extends BaseTest {
    public static final Logger log = LogManager.getLogger(lookup().lookupClass());


    public static void setAllureEnvironment() {
        Map<String, String> envVars = new HashMap<>();
        envVars.put("OS", System.getProperty("os.name"));
        envVars.put("Java", System.getProperty("java.version"));
        envVars.put("Browser", "Chrome 120");
        envVars.put("Environment", "STAGE");
        envVars.put("Appium", "2.0");

        createAllureEnvironmentFile(envVars);
    }



    @Description("Take screenshot for failed Scenario")
    public static void failedTestCaseScreen(Scenario scenario) throws IOException {
        if (scenario.isFailed()){
            log.warn("Test scenario failed: {}", scenario.getName());
            File screenshot = Screenshots.takeshots(
                    MobileDriver.getDriver(),
                    "./screenshots/" + scenario.getName() + ".png"
            );
            Allure.addAttachment("Failure Screenshot", FileUtils.openInputStream(screenshot));
        }
    }
    private static void createAllureEnvironmentFile(Map<String, String> environmentVars) {
        try {
            String resultsDir = System.getProperty("allure.results.directory", "allure-results");
            Path dirPath = Paths.get(resultsDir);

            // Create directory if it doesn't exist
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Create environment file
            Path envFile = dirPath.resolve("environment.properties");
            StringBuilder content = new StringBuilder();

            environmentVars.forEach((key, value) ->
                    content.append(key).append("=").append(value).append("\n")
            );

            Files.write(envFile, content.toString().getBytes());

        } catch (IOException e) {
            throw new RuntimeException("Failed to configure Allure environment", e);
        }
    }
    @Before
    public void setUp() {
        if (MobileDriver.getDriver() == null) {
            MobileDriver.initializeDriver();
            setAllureEnvironment();
        }
    }
    @After(order = 1)
    public void tearDown(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            File screenshot = Screenshots.takeshots(MobileDriver.getDriver(),
                    "./screenshots/" + scenario.getName() + ".png");

            Allure.addAttachment(
                    "Failure Screenshot",
                    FileUtils.openInputStream(screenshot)
            );
        }
    }
}