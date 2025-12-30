package org.example.LoginPage;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ReusePage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;

public class LoginPage extends BasePage {
    AndroidDriver driver;
    private final Logger log = LogManager.getLogger(lookup().lookupClass());

    public LoginPage(AndroidDriver mydriver) {
        super(mydriver);
    }

    private final By usernameField= AppiumBy.accessibilityId("test-Username");
    private final By passwordField=AppiumBy.accessibilityId("test-Password");
    private final By loginButton=AppiumBy.xpath("//android.widget.TextView[@text='LOGIN']");
    private final By shortCut=AppiumBy.xpath("//android.widget.TextView[@text='standard_user']");

    public void Assertion_page_Title_Name(String Page_Name){
        WebDriverWait wait = new WebDriverWait(BasePageDriver, Duration.ofSeconds(30));
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
    public void enterUserName(String username){
        WebDriverWait wait = new WebDriverWait(BasePageDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        BasePageDriver.findElement(usernameField).clear();
        BasePageDriver.findElement(usernameField).sendKeys(username);
    }
    public void enterPassword(String password){
        WebDriverWait wait = new WebDriverWait(BasePageDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        BasePageDriver.findElement(passwordField).clear();
        BasePageDriver.findElement(passwordField).sendKeys(password);
    }
    public void clickLogin(){
        BasePageDriver.findElement(loginButton).click();
    }
}
