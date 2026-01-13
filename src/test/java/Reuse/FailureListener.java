package Reuse;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class FailureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable error = result.getThrowable();
        String aiOutput = SeleniumAIAnalyzer2.analyze(error);
        if (aiOutput != null && !aiOutput.isEmpty()) {
            Allure.addAttachment(
                    "AI Failure Analysis",
                    new ByteArrayInputStream(aiOutput.getBytes(StandardCharsets.UTF_8))
            );
        }
    }
}
