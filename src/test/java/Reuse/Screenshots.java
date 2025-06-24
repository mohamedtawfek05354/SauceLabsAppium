package Reuse;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class Screenshots {
    /**
     * This method captures a screenshot and saves it to the specified path.
     * @param driver The AppiumDriver instance (AndroidDriver or IOSDriver)
     * @param screenshotpath The file path where the screenshot will be saved
     * @return The screenshot file
     * @throws IOException If an error occurs while saving the screenshot
     */
    public static File takeshots(AppiumDriver driver, String screenshotpath) throws IOException {
        // Convert AppiumDriver object to TakesScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) driver);

        // Call getScreenshotAs method to create an image file
        File scrFile = scrShot.getScreenshotAs(OutputType.FILE);

        // Move image file to new destination
        File destFile = new File(screenshotpath);

        // Copy file to the destination
        FileHandler.copy(scrFile, destFile);

        return destFile;
    }
}
