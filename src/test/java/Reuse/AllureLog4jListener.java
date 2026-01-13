package Reuse;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AllureLog4jListener implements IInvokedMethodListener {

    private static final Logger logger = LogManager.getLogger(AllureLog4jListener.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // This can be used if you want to perform some action before each method, but for now it's empty
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // After each test method, attach the log file to the Allure report
        attachLogsToAllure();
    }

    private void attachLogsToAllure() {
        File logFile = new File("logs/application.log"); // Path to your log file
        if (logFile.exists() && logFile.length() > 0) {
            try (FileInputStream fis = new FileInputStream(logFile)) {
                Allure.addAttachment("Application Logs", "text/plain", fis, "log");
            } catch (IOException e) {
                logger.error("Error attaching log to Allure report: ", e);
            }
        } else {
            logger.warn("Log file not found or empty: {}", logFile.getAbsolutePath());
        }
    }
}
