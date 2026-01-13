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

import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.lookup;

public class LoginPage extends BasePage{
    private final Logger log = LogManager.getLogger(lookup().lookupClass());

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    private final By usernameField= AppiumBy.accessibilityId("test-Username");
    private final By passwordField=AppiumBy.accessibilityId("test-Password");
    private final By loginButton=AppiumBy.xpath("//android.widget.TextView[@text='LOGIN']");

    public void Assertion_page_Title_Name(String Page_Name){
        WebElement productTitle1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\""+Page_Name+"\")")));
        Assert.assertEquals(Page_Name, productTitle1.getText());
        log.info("you are in the {} Page and this is correct", Page_Name);
    }
    public void enterUserName(String username){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        BasePageDriver.findElement(usernameField).clear();
        BasePageDriver.findElement(usernameField).sendKeys(username);
    }
    public void enterPassword(String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        BasePageDriver.findElement(passwordField).clear();
        BasePageDriver.findElement(passwordField).sendKeys(password);
    }
    public void clickLogin(){
        BasePageDriver.findElement(loginButton).click();
    }
    public void clickFaildLogin(){
        BasePageDriver.findElement(AppiumBy.xpath("///android.widget.TextView[@text='LOGIN']")).click();
    }
}
