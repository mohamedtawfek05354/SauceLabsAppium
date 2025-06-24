package org.example.ReusePage;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;

public class BasePage {
    public static AndroidDriver BasePageDriver;
    public static final Logger log = LogManager.getLogger(lookup().lookupClass());
    public void Assertion_page_Title_Name(String Page_Name,AndroidDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Option 1: Using corrected UiAutomator locator (recommended for Android)
        WebElement productTitle1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\""+Page_Name+"\")")));

        // Option 2: Alternative XPath (if you prefer)
//         WebElement productTitle2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
//             By.xpath("//android.widget.TextView[@text='"+Page_Name+"']")));

        Assert.assertEquals(Page_Name, productTitle1.getText());
//        Assert.assertEquals("PRODUCTS", productTitle2.getText());
        log.info("you are in the {} Page and this is correct", Page_Name);
    }

    public WebElement findElement(String elementName,AndroidDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        // Option 1: Using corrected UiAutomator locator (recommended for Android)
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\""+elementName+"\")")));

        // Option 2: Alternative XPath (if you prefer)
//         WebElement productTitle2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
//             By.xpath("//android.widget.TextView[@text='"+elementName+"']")));
        log.info("element is {}", elementName);
        return element;
    }
}
