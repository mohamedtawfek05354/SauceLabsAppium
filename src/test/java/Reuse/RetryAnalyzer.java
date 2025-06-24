package Reuse;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.invoke.MethodHandles.lookup;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 3; // Set the maximum number of retries
    private static final Logger log = LogManager.getLogger(lookup().lookupClass());
    CSVDataEntry csvDataEntry;

    @Override
    public boolean retry(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            if (retryCount < maxRetryCount) {
                retryCount++;
                log.warn("Retrying test: {} ({} out of {} retries)", result.getMethod().getMethodName(), retryCount, maxRetryCount);
                logFailure(result);
                return true; // Retry the test
            }
            log.error("Test failed after {} retries: {}", maxRetryCount, result.getMethod().getMethodName());
            return false; // Do not retry if the max retry count is reached
        }
        return false;
    }

    private void logFailure(ITestResult result) {
        String scenarioName = getScenarioName(result);
        String errorMessage = result.getThrowable().getMessage();
        String timestamp = getCurrentTimestamp();

        errorMessage = errorMessage.replace("\"", "\"\"")
                .replace("\n", " ")
                .replace("\r", " ");

        errorMessage = "\"" + errorMessage + "\"";
        csvDataEntry = new CSVDataEntry("./logs/RetryError.csv", true);
        csvDataEntry.writeRow(scenarioName, errorMessage, String.valueOf(retryCount), timestamp);
    }

    private String getScenarioName(ITestResult result) {
        if (result.getParameters().length > 0) {
            return result.getParameters()[0].toString();
        } else {
            return result.getMethod().getMethodName();
        }
    }

    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

}
