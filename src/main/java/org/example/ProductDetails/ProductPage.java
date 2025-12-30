package org.example.ProductDetails;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.bs.A;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ReusePage.BasePage;
import org.example.ReusePage.ScrollingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;

public class ProductPage extends BasePage {
    AndroidDriver driver;
    private final Logger log = LogManager.getLogger(lookup().lookupClass());
    private String productName;
    public ProductPage(AndroidDriver driver) {
        super(driver);
    }
    private final By productDetailsPage= AppiumBy.accessibilityId("test-Description");
    private final By productPriceDetails=AppiumBy.accessibilityId("test-Price");
    private final By openMenu=AppiumBy.accessibilityId("test-Menu");
    private final By allItems=AppiumBy.accessibilityId("test-ALL ITEMS");
    private final By addToCartButton = AppiumBy.xpath("//android.widget.TextView[@text=\"ADD TO CART\"]");
    private final By cartIcon=AppiumBy.accessibilityId("test-Cart");
    private final By checkOut= AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT\")");
    private final By firstName=AppiumBy.accessibilityId("test-First Name");
    private final By lastName=AppiumBy.accessibilityId("test-Last Name");
    private final By zipPostalCode=AppiumBy.accessibilityId("test-Zip/Postal Code");
    private final By continueBtn=AppiumBy.accessibilityId("test-CONTINUE");
    private final By finishBtn=AppiumBy.accessibilityId("test-FINISH");
    private final By endProcess=AppiumBy.androidUIAutomator("new UiSelector().text(\"THANK YOU FOR YOU ORDER\")");

    public void assertDetailsPage(){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailsPage));
        driver.findElement(productDetailsPage).isDisplayed();
    }
    public void clickOnProduct(String productName){
        findElement(productName,driver).click();
        this.productName=productName;
    }
    public String getProductName() {
        return findElement(productName,driver).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPriceDetails).getText();
    }

    public void clickOpenMenu() {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(openMenu));
        driver.findElement(openMenu).click();
    }

    public void clickAllItems() {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(allItems));
        driver.findElement(allItems).click();
    }

    public void clickAddToCart() {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
        driver.findElement(addToCartButton).click();
    }
    public void clickOnCart() {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon));
        driver.findElement(cartIcon).click();
    }

    public void clickOnCheckOut(){
        ScrollingPage.scrollToElement(checkOut,5,driver);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkOut));
        driver.findElement(checkOut).click();
    }
    public void enterFirstName(String fName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(fName);
    }
    public void enterLastName(String lName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(lName);
    }
    public void enterZipCode(String code){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(zipPostalCode));
        driver.findElement(zipPostalCode).clear();
        driver.findElement(zipPostalCode).sendKeys(code);
    }
    public void clickOnContinue(){
        ScrollingPage.scrollToElement(continueBtn,5,driver);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueBtn));
        driver.findElement(continueBtn).click();
    }
    public void clickOnFinish(){
        ScrollingPage.scrollToElement(finishBtn,5,driver);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishBtn));
        driver.findElement(finishBtn).click();
    }
    public void assertThanksMessage(){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(endProcess));
        driver.findElement(endProcess).isDisplayed();
    }

}
